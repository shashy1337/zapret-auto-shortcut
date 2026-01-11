package ru.shashy.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.charset.Charset;

@UtilityClass
public class EnvironmentUtil {

    public static void createEnvironmentVariable(@NonNull String envKey, @NonNull String envPath) {
        if (isEnvExist(envKey)) {
            System.out.printf("[INFO] environment var %s - already exists%n", envKey);
            return;
        }
        try {
            Process process = new ProcessBuilder("cmd", "/c", "setx", envKey, envPath)
                    .redirectErrorStream(true)
                    .start();

            int exit = process.waitFor();
            String output = new String(process.getInputStream().readAllBytes(), Charset.defaultCharset());

            if (exit == 0) {
                System.out.printf("[INFO] environment var %s set to %s%n", envKey, envPath);
            } else {
                System.err.printf("[ERROR] setx exit=%d, output=%s%n", exit, output);
            }

        } catch (IOException e) {
            System.err.printf("[ERROR] environment var %s - not can't set. Exception: %s%n", envKey, e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public static String getEnvironmentVariable(@NonNull String envKey) {
        if (isEnvExist(envKey)) {
            return System.getenv(envKey);
        }
        throw new RuntimeException("[ERROR] environment var " + envKey + " not exists");
    }

    private static boolean isEnvExist(@NonNull String envKey) {
        return System.getenv(envKey) != null && !System.getenv(envKey).isBlank();
    }
}