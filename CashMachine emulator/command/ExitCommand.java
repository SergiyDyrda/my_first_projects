package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Serj on 05.12.2015.
 */
class ExitCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle("com.javarush.test.level26.lesson15.big01.resources.exit_en");
    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));

        if (ConsoleHelper.readString().equals(res.getString("yes")))
            ConsoleHelper.writeMessage(res.getString("thank.message"));
    }
//    @Override
//    public void execute() throws InterruptOperationException
//    {
//        ConsoleHelper.writeMessage("Sure, you want to exit! y/n");
//        String answer = ConsoleHelper.readString();
//        if (answer.equals("y")) {
//            ConsoleHelper.writeMessage("Bye!");
//        }
//    }
}
