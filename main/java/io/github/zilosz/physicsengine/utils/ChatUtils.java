package io.github.zilosz.physicsengine.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> insertBreaks(String string, int maxLength) {
        String[] words = string.split(" ");
        StringBuilder currLine = new StringBuilder();
        List<String> lines = new ArrayList<>();
        int i = 0;

        while (i < words.length) {

            while (i < words.length && currLine.length() + words[i].length() <= maxLength) {
                currLine.append(words[i]).append(" ");
                i++;
            }

            lines.add(currLine.toString());
            currLine = new StringBuilder();
        }

        return lines;
    }
}
