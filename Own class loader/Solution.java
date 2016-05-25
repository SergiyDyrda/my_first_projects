package com.javarush.test.level35.lesson10.bonus01;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/* ClassLoader - что это такое?
Реализуйте логику метода getAllAnimals.
Аргумент метода pathToAnimals - это абсолютный путь к директории, в которой хранятся скомпилированные классы.
Путь не обязательно содержит / в конце.
НЕ все классы наследуются от интерфейса Animal.
НЕ все классы имеют публичный конструктор без параметров.
Только для классов, которые наследуются от Animal и имеют публичный конструктор без параметров, - создать по одному объекту.
Добавить созданные объекты в результирующий сет и вернуть.
Метод main не участвует в тестировании.
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals("D:\\IdeaProjects\\JavaRushHomeWork\\out\\production\\JavaRushHomeWork\\com\\javarush\\test\\level35\\lesson10\\bonus01\\data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> result = new HashSet<>();

        List<Path> classFiles = walkFileTree(pathToAnimals);

        MyClassLoader myClassLoader = new MyClassLoader();
        myClassLoader.loadClasses(classFiles);
        Set<Class> classes = myClassLoader.getClasses();


        for (Class clazz : classes) {
            List<Class> interfaceList = Arrays.asList(clazz.getInterfaces());
            if (interfaceList.contains(Animal.class)) {
                Constructor[] constructors = clazz.getConstructors();
                for (Constructor constructor : constructors) {
                    if (Modifier.isPublic(constructor.getModifiers())) {
                        try {
                            Animal instance = (Animal) constructor.newInstance();
                            result.add(instance);
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                }
            }
        }
        return result;
    }

    static class MyClassLoader extends ClassLoader {
        private Set<Class> classes = new HashSet<>();

        public void loadClasses (List<Path> classes) {
            try {
                for (Path path: classes) {

                    byte[] bytes = Files.readAllBytes(path);

                    Class<?> aClass = defineClass(null, bytes, 0, bytes.length);
                    this.classes.add(aClass);

                }
            } catch (Exception e) {
                   /*NOP*/
            }
        }

        public Set<Class> getClasses() {
            return classes;
        }
    }

    private static List<Path> walkFileTree(String pathToAnimals) {
        final List<Path> files = new ArrayList<>();
        try {
            Files.walkFileTree(Paths.get(pathToAnimals), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".class")) {
                        files.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            return files;
        }
        catch (IOException e) {
            return null;
        }
    }
}
