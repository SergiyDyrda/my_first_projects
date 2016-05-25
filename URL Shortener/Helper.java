package com.javarush.test.level33.lesson15.big01;


import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Segiy on 03.03.2016.
 */
public class Helper {

    private static SecureRandom secureRandom =  new SecureRandom();

    public static String generateRandomString() {
        return new BigInteger(130, secureRandom).toString(32);
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }
}
