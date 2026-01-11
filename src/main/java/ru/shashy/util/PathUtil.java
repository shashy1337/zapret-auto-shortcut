package ru.shashy.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class PathUtil {

    public static String getWindowsStartUpPath() {
        String appData = System.getenv("APPDATA");
        Path startupPath = Paths.get(appData, "Microsoft", "Windows", "Start Menu", "Programs", "Startup");
        return startupPath.toString();
    }

    public static Path createAdditionalPathAndReturn(String path, String assetName, String... additionalPaths)
            throws IOException {

        Path targetPath = (path != null && !path.isBlank())
                ? Paths.get(path)
                : Paths.get(System.getProperty("user.dir"));

        for (String additionalPath : additionalPaths) {
            if (additionalPath != null && !additionalPath.isBlank()) {
                targetPath = targetPath.resolve(additionalPath);
            }
        }

        targetPath = targetPath.resolve(assetName);

        Path parent = targetPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        return targetPath;
    }
}