package com.javarush.test.level33.lesson15.big01;

import com.javarush.test.level33.lesson15.big01.strategies.*;
import com.javarush.test.level33.lesson15.big01.tests.FunctionalTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.JUnit4;
import org.junit.runners.model.InitializationError;

import java.util.*;

/**
 * Created by Segiy on 03.03.2016.
 */
public class Solution {

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> result = new HashSet<>();
        for (String string : strings) {
            result.add(shortener.getId(string));
        }

        return result;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> result = new HashSet<>();
        for (Long key : keys) {
            result.add(shortener.getString(key));
        }

        return result;
    }

    public static void testStrategy(StorageStrategy storageStrategy, long numberElements) {
        Helper.printMessage(storageStrategy.getClass().getSimpleName());

        Set<String> strings = new HashSet<>();

        for (long i = 0; i < numberElements; i++) {
            strings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(storageStrategy);

        long start1 = new Date().getTime();
        Set<Long> keys = getIds(shortener, strings);
        long end1 = new Date().getTime();
        Helper.printMessage(Long.toString(end1 - start1));

        long start2 = new Date().getTime();
        Set<String> stringSet = getStrings(shortener, keys);
        long end2 = new Date().getTime();
        Helper.printMessage(Long.toString(end2 - start2));

        if (stringSet.equals(strings))
            System.out.println("Тест пройден.");
        else
            System.out.println("Тест не пройден.");
    }

    public static void main(String[] args) {

//        HashMapStorageStrategy hashMapStorageStrategy = new HashMapStorageStrategy();
//        testStrategy(hashMapStorageStrategy, 10_000);
//
//        Helper.printMessage("");
//        Helper.printMessage("");
//
//        OurHashMapStorageStrategy ourHashMapStorageStrategy = new OurHashMapStorageStrategy();
//        testStrategy(ourHashMapStorageStrategy, 10_000);
//
//        Helper.printMessage("");
//        Helper.printMessage("");
//
//        FileStorageStrategy fileStorageStrategy = new FileStorageStrategy();
//        testStrategy(fileStorageStrategy, 30);
//
//        Helper.printMessage("");
//        Helper.printMessage("");
//
//        OurHashBiMapStorageStrategy ourHashBiMapStorageStrategy = new OurHashBiMapStorageStrategy();
//        testStrategy(ourHashBiMapStorageStrategy, 10_000);
//
//        Helper.printMessage("");
//        Helper.printMessage("");
//
//        HashBiMapStorageStrategy hashBiMapStorageStrategy = new HashBiMapStorageStrategy();
//        testStrategy(hashBiMapStorageStrategy, 10_000);
//
//        Helper.printMessage("");
//        Helper.printMessage("");
//
//        DualHashBidiMapStorageStrategy dualHashBidiMapStorageStrategy = new DualHashBidiMapStorageStrategy();
//        testStrategy(dualHashBidiMapStorageStrategy, 10_000);

        DataBaseStorageStrategy dataBaseStorageStrategy = new DataBaseStorageStrategy();
        testStrategy(dataBaseStorageStrategy, 100);


//        try {
//            JUnit4 jUnit4 = new JUnit4(FunctionalTest.class);
//            System.out.println(jUnit4.testCount());;
//        }
//        catch (InitializationError initializationError) {
//            initializationError.printStackTrace();
//        }

//        JUnitCore jUnitCore = new JUnitCore();
//        Result result = jUnitCore.runClasses(FunctionalTest.class);
//        if (result.wasSuccessful()) {
//            Helper.printMessage("Test successful");
//        } else {
//            Helper.printMessage("Test failed");
//        }

    }
}

