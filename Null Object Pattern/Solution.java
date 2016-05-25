package com.javarush.test.level31.lesson08.home01;

import java.io.File;
import java.nio.file.Files;

/* Null Object Pattern
Почитайте на вики про паттерн "Null Object"
Используйте Files, чтобы в конструкторе класса Solution правильно инициализировать поле fileData объектом ConcreteFileData.
Если возникли какие-то проблемы со чтением файла по пути pathToFile, то инициализируйте поле объектом NullFileData.
*/
public class Solution {
    private FileData fileData;

    public Solution(String pathToFile) {
        try {
            File file = new File(pathToFile);
            fileData = new ConcreteFileData(Files.isHidden(file.toPath()),
                    Files.isExecutable(file.toPath()),
                    Files.isDirectory(file.toPath()),
                    Files.isWritable(file.toPath()));
        }
        catch (Exception e) {
            fileData = new NullFileData(e);
        }
    }

    public FileData getFileData() {
        return fileData;
    }
}
