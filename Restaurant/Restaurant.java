package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.kitchen.Cook;
import com.javarush.test.level27.lesson15.big01.kitchen.Order;
import com.javarush.test.level27.lesson15.big01.kitchen.Waitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Valk on 04.05.15.
 */
public class Restaurant {

    private final static int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Cook cook1 = new Cook("Amigo");
        cook1.setQueue(QUEUE);
        Cook cook2 = new Cook("Bamigo");
        cook2.setQueue(QUEUE);

        Waitor waitor = new Waitor();
        cook1.addObserver(waitor);
        cook2.addObserver(waitor);

        List<Tablet> tabletList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i + 1);
            tablet.setQueue(QUEUE);
            tabletList.add(tablet);
        }

        Thread cook1Task = new Thread(cook1);
        Thread cook2Task = new Thread(cook2);
        cook1Task.start();
        cook2Task.start();

        Thread task = new Thread(new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL));
        task.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        task.interrupt();

        while (! QUEUE.isEmpty()){
        }

        while ((cook1.isBusy())||(cook2.isBusy())) {}
        cook1Task.interrupt();
        cook2Task.interrupt();


        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();


    }
}
