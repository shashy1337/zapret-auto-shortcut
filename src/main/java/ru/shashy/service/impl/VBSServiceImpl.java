package ru.shashy.service.impl;

import lombok.NonNull;
import ru.shashy.service.VBSService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class VBSServiceImpl implements VBSService {

    private static final String TEMPLATE_FILE_PREFIX = "scvbs-";
    private static final String TEMPLATE_FILE_EXT = ".vbs";

    private static String resolveWScriptPath() {
        String winDir = System.getenv("WINDIR");
        if (winDir != null && !winDir.isBlank()) {
            Path wScript = Path.of(winDir, "System32", "wscript.exe");
            if (Files.isRegularFile(wScript)) {
                return wScript.toString();
            }
        }
        return "wscript.exe";
    }

    @Override
    public void createTempVBS(@NonNull String vbsCode) {
        Path vbsPath = null;
        try {
            vbsPath = Files.createTempFile(TEMPLATE_FILE_PREFIX, TEMPLATE_FILE_EXT);
            try (var writer = Files.newBufferedWriter(vbsPath, Charset.defaultCharset())) {
                writer.write(vbsCode);
            }

            boolean executed = submitVBSScript(vbsPath);
            if (executed) {
                System.out.println("[INFO] VBS script executed successfully");
            } else {
                System.err.println("[ERROR] VBS script finished with non-zero exit code");
            }
        } catch (IOException e) {
            System.err.printf("[ERROR] VBS script could not be created or executed: %s%n", e.getMessage());
            e.printStackTrace();
        } finally {
            if (vbsPath != null) {
                try {
                    Files.deleteIfExists(vbsPath);
                } catch (IOException e) {
                    try {
                        vbsPath.toFile().deleteOnExit();
                    } catch (Throwable ignored) {
                    }
                    System.err.printf("[WARN] Could not delete temp VBS file: %s (%s)%n", vbsPath, e.getMessage());
                }
            }
        }
    }

    private boolean submitVBSScript(@NonNull final Path vbsScript) {
        String wscriptExe = resolveWScriptPath();

        ProcessBuilder pb = new ProcessBuilder(
                wscriptExe,
                "//nologo",
                vbsScript.toAbsolutePath().toString()
        );
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            String output;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()))) {
                output = br.lines().collect(Collectors.joining(System.lineSeparator()));
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                System.err.printf("[ERROR] wscript exited with code %d. Output:%n%s%n", exitCode, output);
                return false;
            }

            if (!output.isBlank()) {
                System.out.println("[INFO] wscript output:");
                System.out.println(output);
            }
            return true;
        } catch (IOException e) {
            System.err.printf("[ERROR] Error starting wscript: %s%n", e.getMessage());
            return false;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("[ERROR] VBS execution interrupted");
            return false;
        }
    }
}