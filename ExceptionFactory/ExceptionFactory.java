package com.javarush.test.level38.lesson06.ExceptionFactory;


/**
 * Created by Segiy on 01.04.2016.
 */
public class ExceptionFactory {

    public static Throwable getException(Enum e) {
        if (e != null) {
            String simpleName = e.getClass().getSimpleName();
            String message = e.toString().toLowerCase().replace("_", " ");
            char[] chars = message.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            message = new String(chars);

            switch (simpleName) {
                case "ExceptionApplicationMessage":
                    return new Exception(message);

                case "ExceptionDBMessage":
                    return new RuntimeException(message);

                case "ExceptionUserMessage":
                    return new Error(message);
            }
        }

        return new IllegalArgumentException();
    }
}
