package ru.shashy.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.shashy.service.AutoCreateClientShortcut;
import ru.shashy.service.VBSService;
import ru.shashy.util.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class AutoCreateClientShortcutImpl implements AutoCreateClientShortcut {

    private static final String SHORTCUT_FILE_NAME = "Zapret.lnk";

    private final VBSService vbsService;

    @Override
    public void createStartUpShortcut(@NonNull String sourceFilePath) {
        createShortcut(sourceFilePath, PathUtil.getStartUpShortcutPathZapret());
    }

    @Override
    public void deleteShortcut() {
        try {
            Path shortcutDir = Paths.get(PathUtil.getStartUpShortcutPathZapret());
            Path shortcutPath = shortcutDir.resolve(SHORTCUT_FILE_NAME);
            if (Files.deleteIfExists(shortcutPath)) {
                System.out.printf("[INFO] Successfully deleted shortcut: %s%n", shortcutPath);
            } else {
                System.out.printf("[INFO] Shortcut not found: %s%n", shortcutPath);
            }
        } catch (IOException e) {
            System.out.printf("[ERROR] Could not delete shortcut: %s (%s)%n",
                    Paths.get(PathUtil.getStartUpShortcutPathZapret()).resolve(SHORTCUT_FILE_NAME),
                    e.getMessage());
        }
    }

    private void createShortcut(@NonNull String sourceFilePath, @NonNull String shortcutTargetDir) {
        try {
            File sourceFile = requireExistingRegularFile(sourceFilePath);
            String sourceFileAbsolutePath = sourceFile.getAbsolutePath();

            Path shortcutDir = Paths.get(shortcutTargetDir);
            Path shortcutPath = shortcutDir.resolve(SHORTCUT_FILE_NAME);

            String vbsCode = """
                    Set wsObj = WScript.CreateObject("WScript.Shell")
                    scPath = "%s"
                    Set scObj = wsObj.CreateShortcut(scPath)
                    scObj.TargetPath = "%s"
                    scObj.WorkingDirectory = "%s"
                    scObj.Save
                    """.formatted(
                    vbsEscape(shortcutPath.toString()),
                    vbsEscape(sourceFileAbsolutePath),
                    vbsEscape(sourceFile.getParent())
            );

            vbsService.createTempVBS(vbsCode);
            System.out.printf("[INFO] Successfully created shortcut: %s -> %s%n", shortcutPath, sourceFileAbsolutePath);
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] File not found: %s%n", sourceFilePath);
        }
    }

    private static File requireExistingRegularFile(@NonNull String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        if (!(f.exists() && f.isFile())) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        return f;
    }

    private static String vbsEscape(String s) {
        return s.replace("\"", "\"\"");
    }
}