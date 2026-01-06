package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Command(name = "install", description = "install latest zapret release asset.")
public class ZapretAutoInstallCommand implements Runnable {

    @ParentCommand
    private ZapretAutoCLI cli;

    @Option(names = {"-d", "--dir"}, description = "Target directory to save zip")
    private String targetDir;

    @Override
    public void run() {
        Path targetPath = cli.getDownloadZapretService().getLatest(targetDir);
        Optional.ofNullable(targetPath).ifPresentOrElse(p -> {
                    cli.getZipService().unzip(p, p.getParent());
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, () -> System.out.println("[WARN] Installing error.")
        );
    }
}