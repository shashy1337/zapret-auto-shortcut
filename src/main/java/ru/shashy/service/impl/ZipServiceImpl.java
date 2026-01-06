package ru.shashy.service.impl;

import lombok.NonNull;
import net.lingala.zip4j.ZipFile;
import ru.shashy.service.ZipService;

import java.io.IOException;
import java.nio.file.Path;

public class ZipServiceImpl implements ZipService {
    @Override
    public void unzip(@NonNull Path zipFilePath, @NonNull Path targetPath) {
        try (ZipFile zipFile = new ZipFile(zipFilePath.toFile())) {
            zipFile.extractAll(targetPath.toString());
            System.out.println("[INFO] Success unzip file");
        } catch (IOException e) {
            System.err.printf("[ERROR] Error unzipping file %s%n", e.getMessage());
        }
    }
}