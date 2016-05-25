package com.javarush.test.level32.lesson15.big01;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Serj on 19.02.2016.
 */
public class HTMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        String name = f.toString();
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex != -1) {
            return (name.substring(dotIndex).equalsIgnoreCase(".html") ||
                    name.substring(dotIndex).equalsIgnoreCase(".htm"));
        } else {
            return f.isDirectory();
        }
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
