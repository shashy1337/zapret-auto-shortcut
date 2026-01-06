package ru.shashy.service;

import lombok.NonNull;

import java.nio.file.Path;

public interface ZipService {

    void unzip(@NonNull Path zipFilePath, @NonNull Path targetPath);

}