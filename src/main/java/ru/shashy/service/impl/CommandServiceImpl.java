package ru.shashy.service.impl;

import lombok.RequiredArgsConstructor;
import ru.shashy.service.AutoCreateClientShortcut;
import ru.shashy.service.CommandService;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private static final List<String> COMMANDS = List.of(
            "0. Set Zapret to StartUp.",
            "1. Delete Zapret from StartUp.",
            "2. Exit app."
    );

    private final AutoCreateClientShortcut autoCreateClientShortcut;

    @Override
    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            loop:
            while (true) {
                COMMANDS.forEach(System.out::println);
                System.out.print("Input the command number (0-2): ");

                if (!scanner.hasNextLine()) {
                    System.out.println("[INFO] Ввод завершён (EOF). Выходим.");
                    break;
                }
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.println("[INFO] Empty input. Repeat your try.");
                    continue;
                }

                int command;
                try {
                    command = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("[INFO] Await number (0, 1 or 2). Repeat.");
                    continue;
                }

                switch (command) {
                    case 0 -> {
                        String path;
                        while (true) {
                            System.out.print("Set Zapret .bat: ");
                            if (!scanner.hasNextLine()) {
                                System.out.println("[INFO] Input on (EOF). Exit.");
                                break loop;
                            }
                            path = scanner.nextLine().trim();
                            if (path.startsWith("\"") && path.endsWith("\"") && path.length() >= 2) {
                                path = path.substring(1, path.length() - 1).trim();
                            }
                            if (path.isEmpty()) {
                                System.out.println("[WARN] Path is empty. Repeat input.");
                                continue;
                            }
                            break;
                        }

                        autoCreateClientShortcut.createStartUpShortcut(path);
                        System.out.println("[INFO] Shortcut created/updated.");
                    }
                    case 1 -> {
                        autoCreateClientShortcut.deleteShortcut();
                        System.out.println("[INFO] Shortcut deleted (if exists).");
                    }
                    case 2 -> {
                        System.out.println("[INFO] Exit app.");
                        break loop;
                    }
                    default -> System.out.println("[INFO] Unknown command: " + command);
                }
            }
        }
    }
}