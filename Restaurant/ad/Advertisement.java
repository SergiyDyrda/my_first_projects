package com.javarush.test.level27.lesson15.big01.ad;

/**
 * Created by Valk on 05.05.15.
 */
public class Advertisement {
    private Object content;
    private String name;
    private long initialAmount; //начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    private int hits; //количество оплаченных показов
    private int duration; //продолжительность в секундах
    private long amountPerOneDisplaying; //стоимость одного показа рекламного объявления в копейках
    private long amountLastDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying = hits > 0 ? initialAmount / hits : 0;
        this.amountLastDisplaying = initialAmount-amountPerOneDisplaying*(hits-1);

    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getHits() {
        return hits;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() throws UnsupportedOperationException{
        if (hits<=0) throw new UnsupportedOperationException();
        hits--;
        if (hits==1) amountPerOneDisplaying = amountLastDisplaying;
    }

}
