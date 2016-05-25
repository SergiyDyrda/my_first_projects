package com.javarush.test.level26.lesson15.big01.command;


import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

/**
 * Created by Serj on 03.12.2015.
 */
interface Command
{
   void execute() throws InterruptOperationException;
}
