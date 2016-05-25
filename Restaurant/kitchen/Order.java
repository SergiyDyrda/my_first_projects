package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.Tablet;

import java.io.IOException;
import java.util.List;

/**
 * Created by Valk on 04.05.15.
 */
public class Order {
    protected List<Dish> dishes;
    private Tablet tablet;

    public Order(Tablet tablet) throws IOException {
        initDishes();
        this.tablet = tablet;
    }

    public int getTotalCookingTime() {
        int result = 0;
        for (Dish dish : dishes) {
            result += dish.getDuration();
        }
        return result;
    }

    protected void initDishes() throws IOException{
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    @Override
    public String toString() {
        if (dishes.size() == 0) return "";
        return String.format("Your order: %s of %s", dishes, tablet, getTotalCookingTime());
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }
}
