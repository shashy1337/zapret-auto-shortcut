package ru.shashy.service.impl.commands;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import ru.shashy.service.AutoClientShortcut;
import ru.shashy.service.DownloadZapretService;
import ru.shashy.service.ZipService;

@Command(
        name = "zapret-auto",
        mixinStandardHelpOptions = true,
        description = "Zapret-auto CLI helper",
        version = "1.0.0",
        subcommands = {
                ZapretAutoDownloadCommand.class,
                ZapretAutoInstallCommand.class,
                ZapretAutoCreateShortcut.class
        }
)
@Getter
@RequiredArgsConstructor
public class ZapretAutoCLI implements Runnable {

    @NonNull
    private final DownloadZapretService downloadZapretService;

    @NonNull
    private final ZipService zipService;

    @NonNull
    private final AutoClientShortcut autoClientShortcut;

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}