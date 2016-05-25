package com.javarush.test.level33.lesson15.big01.tests;

import com.javarush.test.level33.lesson15.big01.Helper;
import com.javarush.test.level33.lesson15.big01.Shortener;
import com.javarush.test.level33.lesson15.big01.strategies.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Segiy on 11.03.2016.
 */

public class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String first = Helper.generateRandomString();
        String second = Helper.generateRandomString();
        String third = new String(first.getBytes());

        Long id1 = shortener.getId(first);
        Long id2 = shortener.getId(second);
        Long id3 = shortener.getId(third);

        Assert.assertNotEquals(id2, id1);
        Assert.assertNotEquals(id2, id3);
        Assert.assertEquals(id1, id3);

        String getFirst = shortener.getString(id1);
        String getSecond = shortener.getString(id2);
        String getThird = shortener.getString(id3);

        Assert.assertEquals(first, getFirst);
        Assert.assertEquals(second, getSecond);
        Assert.assertEquals(third, getThird);
    }

    @Test
    public void testHashMapStorageStrategy() {
        HashMapStorageStrategy storageStrategy = new HashMapStorageStrategy();
        testStorage(new Shortener(storageStrategy));
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        OurHashMapStorageStrategy storageStrategy = new OurHashMapStorageStrategy();
        testStorage(new Shortener(storageStrategy));
    }

    @Test
    public void testFileStorageStrategy() {
        FileStorageStrategy storageStrategy = new FileStorageStrategy();
        testStorage(new Shortener(storageStrategy));
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        HashBiMapStorageStrategy storageStrategy = new HashBiMapStorageStrategy();
        testStorage(new Shortener(storageStrategy));
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        DualHashBidiMapStorageStrategy storageStrategy = new DualHashBidiMapStorageStrategy();
        testStorage(new Shortener(storageStrategy));
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        OurHashBiMapStorageStrategy storageStrategy = new OurHashBiMapStorageStrategy();
        testStorage(new Shortener(storageStrategy));
    }
}
