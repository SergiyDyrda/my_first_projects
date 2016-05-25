package com.javarush.test.level33.lesson15.big01.strategies;

/**
 * Created by Segiy on 09.03.2016.
 */
public class OurHashMapStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    int INITIAL_CAPACITY;
    Entry[] table = new Entry[INITIAL_CAPACITY];
    int size;
    int threshold;
    float loadFactor;

    public OurHashMapStorageStrategy() {
        INITIAL_CAPACITY = DEFAULT_INITIAL_CAPACITY;
        loadFactor = DEFAULT_LOAD_FACTOR;
        threshold = (int) (INITIAL_CAPACITY * loadFactor);
    }

    public OurHashMapStorageStrategy(int capacity) {
        INITIAL_CAPACITY = capacity;
        loadFactor = DEFAULT_LOAD_FACTOR;
        threshold = (int) (INITIAL_CAPACITY * loadFactor);
    }

    public OurHashMapStorageStrategy(int capacity, float loadFactor) {
        INITIAL_CAPACITY = capacity;
        this.loadFactor = loadFactor;
        threshold = (int) (INITIAL_CAPACITY * this.loadFactor);
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
        for (Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            if ((e.hash == hash) && (e.key.equals(key)))
                return e;
        }

        return null;
    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        if (size >= threshold && table[bucketIndex] == null) {
            resize(2 * table.length);
            hash = (key != null) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }

    void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    void transfer(Entry[] newTable) {
        int newCapacity = newTable.length;
        for (Entry e : table) {
            while (e != null) {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
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
        for (Entry e : table) {
            while (e != null) {
                if (value.equals(e.value)) {
                    return e.key;
                }
                e = e.next;
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);

        return entry.value;
    }
}
