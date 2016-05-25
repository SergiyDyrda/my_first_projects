package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.ad.AdvertisementManager;
import com.javarush.test.level27.lesson15.big01.ad.NoVideoAvailableException;
import com.javarush.test.level27.lesson15.big01.kitchen.Order;
import com.javarush.test.level27.lesson15.big01.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Valk on 04.05.15.
 */
public class Tablet {
    public static java.util.logging.Logger logger = Logger.getLogger(Tablet.class.getName());
    private final int number;
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException nvae) {
            logger.log(Level.INFO, "No video is available for the order " + order.toString());
        }
    }

    public void createTestOrder() {
        Order order = null;
        try {
            order = new TestOrder(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException nvae) {
            logger.log(Level.INFO, "No video is available for the order " + order.toString());
        }
    }

    private void processOrder(Order order) {
        ConsoleHelper.writeMessage(order.toString());
        if (!order.isEmpty()) {
            try {
                queue.put(order);
            } catch (InterruptedException e) {
            }
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            advertisementManager.processVideos();
        }
    }

    @Override
    public String toString() {
        return "Tablet{" + "number=" + number + '}';
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
