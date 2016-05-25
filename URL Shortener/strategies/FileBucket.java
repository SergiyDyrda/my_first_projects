package com.javarush.test.level33.lesson15.big01.strategies;

import com.javarush.test.level33.lesson15.big01.ExceptionHandler;
import com.javarush.test.level33.lesson15.big01.Helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Segiy on 09.03.2016.
 */
public class FileBucket {
    Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(Helper.generateRandomString(), "");
            path.toFile().deleteOnExit();
        }
        catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }

    public long getFileSize() {
        return path.toFile().length();
    }

    public void putEntry(Entry entry) {
        try  {
            FileOutputStream fous = new FileOutputStream(path.toFile());
            ObjectOutputStream ous = new ObjectOutputStream(fous);
            ous.writeObject(entry);
            fous.close();
            ous.close();
        }
        catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }

    public Entry getEntry() {
        Entry result = null;

        if (path.toFile().length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream inputStream = new ObjectInputStream(fis);
                Object object = inputStream.readObject();
                fis.close();
                inputStream.close();
                result = (Entry) object;
            }
            catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
        return result;
    }

    public void remove() {
        try {
            Files.delete(path);
        }
        catch (IOException e) {
            ExceptionHandler.log(e);
        }
    }
}
