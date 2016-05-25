package com.javarush.test.level33.lesson15.big01.strategies;

import com.javarush.test.level33.lesson15.big01.strategies.Entry;
import com.javarush.test.level33.lesson15.big01.strategies.FileBucket;

/**
 * Created by Segiy on 09.03.2016.
 */
public class FileStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
    long bucketSizeLimit = 10_000;

    public FileStorageStrategy() {
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            table[i] = new FileBucket();
        }
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    int hash(Long k) {
        int h;
        h = k.hashCode();

        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    Entry getEntry(Long key) {
        if (size == 0)
            return null;

        int hash = key != 0 ? hash(key) : 0;
        int i = indexFor(hash, table.length);

            Entry e = table[i].getEntry();
            while (e != null) {
                if ((e.hash == hash) && (e.key.equals(key)))
                    return e;

                e = e.next;
        }

        return null;
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {

        FileBucket bucket = createEntry(hash, key, value, bucketIndex);

        if (bucket.getFileSize() >= bucketSizeLimit) {
            resize(2 * table.length);
        }
    }

    FileBucket createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        Entry entry = new Entry(hash, key, value, e);
        table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(entry);
        size++;

        return table[bucketIndex];
    }

    void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newTable[i] = new FileBucket();
        }
        transfer(newTable);
        table = newTable;
    }

    void transfer(FileBucket[] newTable) {
        int newCapacity = newTable.length;
        for (FileBucket entry : table) {
            Entry e = entry.getEntry();
            while (e != null) {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i].getEntry();
                newTable[i].putEntry(e);
                e = next;
            }

            entry.remove();
        }
    }

    @Override
    public boolean containsKey(Long key) {
        Entry entry = getEntry(key);
        return entry != null ? true : false;
    }

    @Override
    public boolean containsValue(String value) {
        Long key = getKey(value);
        return key != null ? true : false;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        addEntry(hash, key, value, i);
    }

    @Override
    public Long getKey(String value) {
        for (FileBucket e : table) {

            Entry entry = e.getEntry();
            while (entry != null) {
                if (entry.value.equals(value))
                    return entry.key;

                entry = entry.next;
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);

        return (entry != null) ? entry.value : null;
    }
}
