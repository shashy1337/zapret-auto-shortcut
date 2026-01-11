package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import ru.shashy.enums.ZapretEnum;
import ru.shashy.util.PathUtil;

import java.io.IOException;
import java.nio.file.Path;

@Command(name = "download", description = "Download latest zapret release asset.")
public class ZapretAutoDownloadCommand implements Runnable {

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
            cli.getDownloadZapretService().getLatest(targetDirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}