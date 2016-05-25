package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valk on 04.05.15.
 */
public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message){
        System.out.println(message);
    }
    public static String readString() throws IOException {
        String result = null;
            result = reader.readLine();
        return result;
    }
    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishList = new ArrayList<>();
        writeMessage("Выберите блюда для заказа. Для окончания выбора введите exit");
        writeMessage(Dish.allDishesToString());
        while (true){
            Dish dish = getValidDishName();
            if (dish == null) return dishList;
            dishList.add(dish);
        }
    }

    private static Dish getValidDishName() throws IOException {
        while (true){
            String dishName = readString();
            if ("exit".equals(dishName)) return null;
            for (Dish dish : Dish.values()){
                if (dishName.equals(dish.toString())){
                    return Dish.valueOf(dishName);
                }
            }
            writeMessage(String.format("%s is not detected",dishName));
        }
    }
}
