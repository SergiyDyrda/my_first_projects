package com.javarush.test.level37.lesson04.big01.female;

import com.javarush.test.level37.lesson04.big01.AbstractFactory;
import com.javarush.test.level37.lesson04.big01.Human;

/**
 * Created by CMI-USER on 1/25/2016.
 */
public class FemaleFactory implements AbstractFactory
{
    public Human getPerson(int age)
    {
        Human human = null;
        if (age > 19)
            human = new Woman();
        else if (age > 12 && age <=19)
            human = new TeenGirl();
        else
            human = new KidGirl();
        return human;
    }
}
