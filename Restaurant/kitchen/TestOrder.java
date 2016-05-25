package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.Tablet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Valk on 10.05.15.
 */
public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        int dishCount = (int)(Math.random()*(Dish.values().length-2)+1);
        this.dishes = new ArrayList<>();
        for (int i = 0; i<dishCount; i++){
            int dishNumber = (int)(Math.random()*(Dish.values().length-1));
            this.dishes.add(Dish.values()[dishNumber]);
        }
    }
}
