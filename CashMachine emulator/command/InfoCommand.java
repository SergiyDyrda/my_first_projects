package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;

import java.util.ResourceBundle;


/**
 * Created by Serj on 04.12.2015.
 */
class InfoCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle("com.javarush.test.level26.lesson15.big01.resources.info_en");

    @Override
    public void execute()
    {
        boolean money = false;
        ConsoleHelper.writeMessage(res.getString("before"));
        for (CurrencyManipulator cur : CurrencyManipulatorFactory.getAllCurrencyManipulators()){
            if (cur.hasMoney()){
                if (cur.getTotalAmount() > 0)
                {
                    ConsoleHelper.writeMessage(cur.getCurrencyCode() + " - " + cur.getTotalAmount());
                    money = true;
                }
            }
        }
        if (!money)
            ConsoleHelper.writeMessage(res.getString("no.money"));
    }

//    @Override
//    public void execute()
//    {
//        boolean money = false;
//        for (CurrencyManipulator cur : CurrencyManipulatorFactory.getAllCurrencyManipulators()){
//            if (cur.hasMoney()){
//                if (cur.getTotalAmount() > 0)
//                {
//                    ConsoleHelper.writeMessage(cur.getCurrencyCode() + " - " + cur.getTotalAmount());
//                    money = true;
//                }
//            }
//        }
//        if (!money)
//            ConsoleHelper.writeMessage("No money available.");
//    }
}

