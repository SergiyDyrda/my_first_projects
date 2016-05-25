package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.command.CommandExecutor;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.Locale;

/**
 * Created by Serj on 30.11.2015.
 */
public class CashMachine
{

    public static final String RESOURCE_PATH = "com.javarush.test.level26.lesson15.big01.resources.";

    public static void main(String[] args)
    {
        try
        {
            Locale.setDefault(Locale.ENGLISH);
            CommandExecutor.execute(Operation.LOGIN);
            Operation currentOperation;
            do
            {
                currentOperation = ConsoleHelper.askOperation();
                CommandExecutor.execute(currentOperation);
            } while (currentOperation != Operation.EXIT);
        }
        catch (InterruptOperationException e)
        {
            ConsoleHelper.writeMessage("Bye!");
        }
    }
}

