package ru.shashy.service;

import java.nio.file.Path;

public interface DownloadZapretService {

    Path getLatest(String path);

}