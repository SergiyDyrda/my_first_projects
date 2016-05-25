package com.javarush.test.level33.lesson15.big01;

import com.javarush.test.level33.lesson15.big01.strategies.StorageStrategy;

/**
 * Created by Segiy on 03.03.2016.
 */
public class Shortener {

    private StorageStrategy storageStrategy;
    private Long lastId = Long.valueOf(0);

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string) {
            if (storageStrategy.containsValue(string))
                return storageStrategy.getKey(string);
            else {
                storageStrategy.put(++lastId, string);
                return lastId;
            }
    }

    public synchronized String getString(Long id) {
            if (storageStrategy.containsKey(id)) {
                return storageStrategy.getValue(id);
            } else {
                return null;
            }
    }
}
