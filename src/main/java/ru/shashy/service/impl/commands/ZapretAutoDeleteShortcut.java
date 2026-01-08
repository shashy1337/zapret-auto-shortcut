package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "delete-shortcut", description = "Delete windows shortcut in windows StartUp folder.")
public class ZapretAutoDeleteShortcut implements Runnable{

    @ParentCommand
    private ZapretAutoCLI cli;

    @Override
    public void run() {
        cli.getAutoClientShortcut().delete();
        System.out.println("[INFO] Shortcut delete: OK");
    }
}