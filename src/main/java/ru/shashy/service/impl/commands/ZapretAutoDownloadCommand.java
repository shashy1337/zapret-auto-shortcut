package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

import java.nio.file.Path;

@Command(name = "download", description = "Download latest zapret release asset.")
public class ZapretAutoDownloadCommand implements Runnable {

    @ParentCommand
    private ZapretAutoCLI cli;

    @Option(names = {"-d", "--dir"}, description = "Target directory to save zip")
    private String targetDir;

    @Override
    public void run() {
        Path path = cli.getDownloadZapretService().getLatest(targetDir);
        System.out.println(path != null ? "[INFO] Download OK" : "[WARN] Download failed");
    }
}