package ru.shashy.util;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class PathUtil {
    public static String getStartUpShortcutPathZapret() {
        String appData = System.getenv("APPDATA");
        Path startupPath = Paths.get(appData, "Microsoft", "Windows", "Start Menu", "Programs", "Startup");
        return startupPath.toString();
    }
}