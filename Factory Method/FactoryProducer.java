package com.javarush.test.level37.lesson04.big01;

import com.javarush.test.level37.lesson04.big01.female.FemaleFactory;
import com.javarush.test.level37.lesson04.big01.male.MaleFactory;

/**
 * Created by CMI-USER on 1/25/2016.
 */
public class FactoryProducer
{
    public static enum HumanFactoryType
    {
        MALE,
        FEMALE
    }

    public static AbstractFactory getFactory(HumanFactoryType humanFactoryType)
    {
        if (humanFactoryType == HumanFactoryType.MALE)
            return new MaleFactory();
        else
            return new FemaleFactory();
    }
}
