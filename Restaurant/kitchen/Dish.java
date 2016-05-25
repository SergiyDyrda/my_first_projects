package com.javarush.test.level27.lesson15.big01.kitchen;

/**
 * Created by Valk on 04.05.15.
 */
public enum Dish {
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private int duration;

    Dish(int i) {
        this.duration = i;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Dish.values().length - 1; i++) {
            result.append(Dish.values()[i]).append(", ");
        }
        for (int i = Dish.values().length - 1; i < Dish.values().length; i++) {
            result.append(Dish.values()[i]);
        }
        return result.toString();
    }

}
