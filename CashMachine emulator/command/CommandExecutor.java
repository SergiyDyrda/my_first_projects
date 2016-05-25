package com.javarush.test.level26.lesson15.big01.command;


import com.javarush.test.level26.lesson15.big01.Operation;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serj on 04.12.2015.
 */
public class CommandExecutor
{
    private CommandExecutor()
    {
    }

    private static Map<Operation, Command> operations;

    static
    {
        operations = new HashMap<>();
        operations.put(Operation.LOGIN, new LoginCommand());
        operations.put(Operation.DEPOSIT, new DepositCommand());
        operations.put(Operation.INFO, new InfoCommand());
        operations.put(Operation.WITHDRAW, new WithdrawCommand());
        operations.put(Operation.EXIT, new ExitCommand());
    }

    public static final void execute(Operation operation) throws InterruptOperationException
    {
        operations.get(operation).execute();
    }
}
