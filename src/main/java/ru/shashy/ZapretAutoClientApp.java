package ru.shashy;

import picocli.CommandLine;
import ru.shashy.config.AppConfig;
import ru.shashy.service.VBSService;
import ru.shashy.service.impl.AutoClientWindowsShortcutImpl;
import ru.shashy.service.impl.DownloadZapretServiceImpl;
import ru.shashy.service.impl.VBSServiceImpl;
import ru.shashy.service.impl.ZipServiceImpl;
import ru.shashy.service.impl.commands.ZapretAutoCLI;

public class ZapretAutoClientApp {

    private static final AppConfig APP_CONFIG = new AppConfig();

    public static void main(String[] args) {
        var downloadService = new DownloadZapretServiceImpl(APP_CONFIG.getObjectMapper(), APP_CONFIG.getHttpClient());
        var zipService = new ZipServiceImpl();

        var vbsService = new VBSServiceImpl();
        var autoClientShortcut = new AutoClientWindowsShortcutImpl(vbsService);

        int exitCode = new CommandLine(new ZapretAutoCLI(downloadService, zipService, autoClientShortcut)).execute(args);
        System.exit(exitCode);
    }
}