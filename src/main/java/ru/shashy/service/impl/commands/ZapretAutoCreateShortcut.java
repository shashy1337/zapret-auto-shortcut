package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(name = "create-shortcut", description = "Create windows shortcut in windows StartUp folder.")
public class ZapretAutoCreateShortcut implements Runnable {

    @ParentCommand
    private ZapretAutoCLI cli;

    @Option(names = {"-p", "--path"}, description = "Target directory to save zip")
    private String shortcutPath;

    @Override
    public void run() {
        cli.getAutoClientShortcut().create(shortcutPath);
        System.out.println("[INFO] Shortcut create: OK");
    }
}