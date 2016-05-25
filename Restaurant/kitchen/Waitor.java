package com.javarush.test.level27.lesson15.big01.kitchen;


import com.javarush.test.level27.lesson15.big01.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Valk on 05.05.15.
 */
public class Waitor implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ConsoleHelper.writeMessage(String.format("%s was cooked by %s", ((Order) arg).toString(), ((Cook) o).toString()));
    }
}
