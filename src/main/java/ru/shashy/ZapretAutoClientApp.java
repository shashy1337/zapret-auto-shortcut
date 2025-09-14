package ru.shashy;

import ru.shashy.service.AutoCreateClientShortcut;
import ru.shashy.service.CommandService;
import ru.shashy.service.VBSService;
import ru.shashy.service.impl.AutoCreateClientShortcutImpl;
import ru.shashy.service.impl.CommandServiceImpl;
import ru.shashy.service.impl.VBSServiceImpl;

public class ZapretAutoClientApp {
    public static void main(String[] args) {
        VBSService vbsService = new VBSServiceImpl();
        AutoCreateClientShortcut autoCreateClientShortcut = new AutoCreateClientShortcutImpl(vbsService);
        CommandService commandService = new CommandServiceImpl(autoCreateClientShortcut);
        commandService.start();
    }
}