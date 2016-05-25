package com.javarush.test.level27.lesson15.big01.ad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Valk on 09.05.15.
 */
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance = new StatisticAdvertisementManager();
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    public static StatisticAdvertisementManager getInstance() {
        return instance;
    }

    private StatisticAdvertisementManager() {
    }

    public List<Advertisement> getAdvertisementList(boolean active) {
        List<Advertisement> result = new ArrayList<>(advertisementStorage.list());
        for (Iterator<Advertisement> iterator = result.iterator(); iterator.hasNext(); ) {
            int hits = iterator.next().getHits();
            if ((active && hits < 1) || (!active && hits != 0)) {
                iterator.remove();
            }
        }
        return result;
    }
}
