package com.javarush.test.level27.lesson15.big01;

import java.util.List;

/**
 * Created by Valk on 10.05.15.
 */
public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval){
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            int tabletNumber = (int)(Math.random()*(tablets.size()));
            tablets.get(tabletNumber).createTestOrder();
            try {Thread.sleep(interval);} catch (InterruptedException e){break;}
        }
    }
}
