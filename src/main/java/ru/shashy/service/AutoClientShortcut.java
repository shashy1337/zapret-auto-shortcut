package ru.shashy.service;

import lombok.NonNull;

public interface AutoClientShortcut {

   void create(@NonNull String sourceFilePath);

   void delete();

}