package ru.shashy.service.impl.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;
import ru.shashy.enums.ZapretEnum;
import ru.shashy.util.EnvironmentUtil;
import ru.shashy.util.PathUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Command(name = "update", description = "Update zapret asset.")
public class ZapretAutoUpdateCommand implements Runnable {

    @ParentCommand
    private ZapretAutoCLI cli;

    @Override
    public void run() {
        try {
            String zapretPath = EnvironmentUtil.getEnvironmentVariable(ZapretEnum.ZAPRET_AUTO_NAME.getName());
            Path targetUpdate = PathUtil.createAdditionalPathAndReturn(zapretPath, ZapretEnum.ASSET_NAME.getName());
            Path zipPath = cli.getDownloadZapretService().getLatest(targetUpdate).orElseThrow(
                    () -> new RuntimeException("[ERROR] Illegal Path")
            );
            cli.getZipService().unzip(zipPath, Path.of(zapretPath));
            Files.deleteIfExists(targetUpdate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}