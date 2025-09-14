package ru.shashy.service;

import lombok.NonNull;

public interface AutoCreateClientShortcut {
   void createStartUpShortcut(@NonNull String sourceFilePath);

   void deleteShortcut();
}