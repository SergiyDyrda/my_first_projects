package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Valk on 05.05.15.
 */
public class Cook extends Observable implements Runnable {
    private String name;
    private volatile boolean busy;
    private LinkedBlockingQueue<Order> queue;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        ConsoleHelper.writeMessage(String.format("Start cooking - %s, cooking time %dmin", order.toString(), order.getTotalCookingTime()));
        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {
        }
        StatisticEventManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(), this.name, order.getTotalCookingTime() * 60, order.getDishes()));
        this.setChanged();
        this.notifyObservers(order);
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (! Thread.currentThread().isInterrupted()) {
                Order order = queue.take();
                startCookingOrder(order);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            return;
        }

    }
}
