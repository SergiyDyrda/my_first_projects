package com.javarush.test.level37.lesson04.big01.male;

import com.javarush.test.level37.lesson04.big01.AbstractFactory;
import com.javarush.test.level37.lesson04.big01.Human;

/**
 * Created by CMI-USER on 1/25/2016.
 */
public class MaleFactory implements AbstractFactory
{
    public Human getPerson(int age)
    {
        Human human = null;
        if (age > 19)
            human = new Man();
        else if (age > 12 && age <=19)
            human = new TeenBoy();
        else
            human = new KidBoy();
        return human;
    }
}
