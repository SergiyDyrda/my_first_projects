package com.javarush.test.level26.lesson15.big01;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serj on 03.12.2015.
 */
public class CurrencyManipulatorFactory
{
    private static Map<String, CurrencyManipulator> manipulators = new HashMap<>();

    private CurrencyManipulatorFactory()
    {

    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode)
    {

        if (!manipulators.containsKey(currencyCode))
        {
            manipulators.put(currencyCode, new CurrencyManipulator(currencyCode));
        }
        return manipulators.get(currencyCode);
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return manipulators.values();
    }
}
