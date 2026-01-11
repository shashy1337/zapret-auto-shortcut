package ru.shashy.service;

import java.nio.file.Path;
import java.util.Optional;

public interface DownloadZapretService {

    Optional<Path> getLatest(Path targetPath);

}