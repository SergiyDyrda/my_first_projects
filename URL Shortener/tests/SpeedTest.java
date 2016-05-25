package com.javarush.test.level33.lesson15.big01.tests;

import com.google.common.base.Stopwatch;
import com.javarush.test.level33.lesson15.big01.Helper;
import com.javarush.test.level33.lesson15.big01.Shortener;
import com.javarush.test.level33.lesson15.big01.strategies.HashBiMapStorageStrategy;
import com.javarush.test.level33.lesson15.big01.strategies.HashMapStorageStrategy;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

/**
 * Created by Segiy on 11.03.2016.
 */
public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
//        Stopwatch stopwatch = Stopwatch.createStarted();
        long start = System.currentTimeMillis();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }

        return System.currentTimeMillis() - start;
//        return stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
//        Stopwatch stopwatch = Stopwatch.createStarted();
        long start = System.currentTimeMillis();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }

        return System.currentTimeMillis() - start;
//        return stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new TreeSet<>();
        for (int i = 0; i < 10_000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        HashSet<Long> ids1 = new HashSet<>();
        HashSet<Long> ids2 = new HashSet<>();

        long idTime1 = getTimeForGettingIds(shortener1, origStrings, ids1);
        long idTime2 = getTimeForGettingIds(shortener2, origStrings, ids2);

        assertTrue(idTime1 > idTime2);

        long stringTime1 = getTimeForGettingStrings(shortener1, ids1, new HashSet<String>());
        long stringTime2 = getTimeForGettingStrings(shortener2, ids2, new HashSet<String>());

        assertEquals(stringTime1, stringTime2, 5);
    }
}
