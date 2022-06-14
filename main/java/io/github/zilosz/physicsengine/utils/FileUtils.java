package io.github.zilosz.physicsengine.utils;

import java.io.File;

public class FileUtils {

    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        return lastIndexOf == -1 ? "" : name.substring(lastIndexOf);
    }
}
