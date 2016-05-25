package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.ad.Advertisement;
import com.javarush.test.level27.lesson15.big01.ad.StatisticAdvertisementManager;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;

import java.util.*;

/**
 * Created by Valk on 08.05.15.
 */
public class DirectorTablet {
    public void printAdvertisementProfit() {
        TreeMap<String, Long> map = (TreeMap<String, Long>) StatisticEventManager.getInstance().getAdvProfitByDays();
        long total = 0;
        for (Map.Entry<String, Long> pair : map.descendingMap().entrySet()) {
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %.2f", pair.getKey(), (double) pair.getValue() / 100));
            total += pair.getValue();
        }
        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", (double) total / 100));
    }

    public void printCookWorkloading() {
        TreeMap<String, Map<String, Integer>> map = (TreeMap<String, Map<String, Integer>>) StatisticEventManager.getInstance().getCookWorkByDays();
        for (Map.Entry<String, Map<String, Integer>> pair : map.descendingMap().entrySet()) {
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s", pair.getKey()));
            for (Map.Entry<String, Integer> cookPair : pair.getValue().entrySet()) {
                if (cookPair.getValue() != 0)
                    ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %d min", cookPair.getKey(), (cookPair.getValue() + 59) / 60));
            }
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> list = StatisticAdvertisementManager.getInstance().getAdvertisementList(true);
        Collections.sort(list, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (Advertisement advertisement : list) {
            ConsoleHelper.writeMessage(String.format("%s - %d", advertisement.getName(), advertisement.getHits()));
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> list = StatisticAdvertisementManager.getInstance().getAdvertisementList(false);
        Collections.sort(list, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        for (Advertisement advertisement : list) {
            ConsoleHelper.writeMessage(String.format("%s", advertisement.getName()));
        }
    }
}
