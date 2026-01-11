package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import ru.shashy.enums.ZapretEnum;
import ru.shashy.util.EnvironmentUtil;
import ru.shashy.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Command(name = "install", description = "install latest zapret release asset.")
public class ZapretAutoInstallCommand implements Runnable {

    @ParentCommand
    private ZapretAutoCLI cli;

    @Option(names = {"-d", "--dir"}, description = "Target directory to save zip")
    private String targetDir;

    @Override
    public void run() {
        Path targetDirPath;
        try {
            targetDirPath = PathUtil.createAdditionalPathAndReturn(
                    targetDir,
                    ZapretEnum.ASSET_NAME.getName(),
                    ZapretEnum.ZAPRET_AUTO_NAME.getName()
            );
            Path parent = targetDirPath.getParent();
            cli.getZipService().unzip(targetDirPath, parent);
            Files.delete(targetDirPath);
            EnvironmentUtil.createEnvironmentVariable(parent.getFileName().toString(), targetDirPath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}