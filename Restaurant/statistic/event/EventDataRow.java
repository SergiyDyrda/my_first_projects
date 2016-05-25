package com.javarush.test.level27.lesson15.big01.statistic.event;

import java.util.Date;

/**
 * Created by Valk on 07.05.15.
 */
public interface EventDataRow {
    EventType getType();
    Date getDate();
    int getTime();
}
