package com.nameof.raftkv;

import cn.hutool.core.lang.Tuple;
import com.google.common.collect.Sets;

import java.util.Set;

public class LogFormatter {
    public static final String SET = "set";
    public static final String DELETE = "delete";
    public static final String CLEAR = "clear";
    private static final Set<String> ALL = Sets.newHashSet(SET, DELETE, CLEAR);

    public static String log(String command, String key, String value) {
        switch (command) {
            case SET:
                return String.format("%s %s %s", command, key, value);
            case DELETE:
                return String.format("%s %s", command, key);
            case CLEAR:
                return String.format("%s", command);
            default:
                throw new IllegalArgumentException("Invalid command: " + command);
        }
    }

    public static Tuple parseLogEntry(String logEntry) {
        String[] parts = logEntry.split(" ");
        if (parts.length < 1) {
            throw new IllegalArgumentException("Invalid log entry format.");
        }

        String command = parts[0];
        if (!ALL.contains(command)) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }

        if (CLEAR.equals(command)) {
            return new Tuple(command, null, null);
        }

        String key = parts[1];
        String value = null;

        // 处理具有值的命令
        if (SET.equals(command)) {
            if (parts.length == 3) {
                value = parts[2];
            } else {
                throw new IllegalArgumentException("Invalid log entry format for command: " + command);
            }
        }

        return new Tuple(command, key, value);
    }
}
