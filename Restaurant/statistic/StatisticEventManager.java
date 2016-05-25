package com.javarush.test.level27.lesson15.big01.statistic;

import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventType;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Valk on 07.05.15.
 */
public class StatisticEventManager {
    private static StatisticEventManager instance = new StatisticEventManager();
    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticEventManager() {
    }

    public static StatisticEventManager getInstance() {
        return instance;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<String, Long> getAdvProfitByDays() {
        List<EventDataRow> list = statisticStorage.getEventTypeList(EventType.SELECTED_VIDEOS);
        Map<String, Long> result = new TreeMap<>();
        for (EventDataRow video : list) {
            String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(((VideoSelectedEventDataRow) video).getDate());
            long currAmount = ((VideoSelectedEventDataRow) video).getAmount();
            Long amount = result.get(date);
            result.put(date, amount == null ? currAmount : amount + currAmount);
        }
        return result;
    }

    public Map<String, Map<String, Integer>> getCookWorkByDays() {
        List<EventDataRow> list = statisticStorage.getEventTypeList(EventType.COOKED_ORDER);
        Map<String, Map<String, Integer>> result = new TreeMap<>();
        for (EventDataRow order : list) {
            String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(((CookedOrderEventDataRow) order).getDate());
            String currName = ((CookedOrderEventDataRow) order).getCookName();
            int currTime = ((CookedOrderEventDataRow) order).getTime();

            Map<String, Integer> cookMap = result.get(date);
            if (cookMap == null) {
                cookMap = new TreeMap<>();
                result.put(date, cookMap);
            }
            Integer cookTime = cookMap.get(currName);
            cookMap.put(currName, cookTime == null ? currTime : cookTime + currTime);
        }
        return result;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> map = new HashMap<>();

        public StatisticStorage() {
            for (EventType et : EventType.values()) {
                map.put(et, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            map.get(data.getType()).add(data);
        }

        private List<EventDataRow> getEventTypeList(EventType eventType) {
            return map.get(eventType);
        }

    }
}
