package com.javarush.test.level21.lesson16.big01;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serj on 15.11.2015.
 */
public class Hippodrome
{
    public static Hippodrome game;
    private ArrayList<Horse> horses = new ArrayList<>();


    public static void main(String[] args)
    {
        game = new Hippodrome();
        game.getHorses().add(new Horse("House1", 3.0d, 0.0d));
        game.getHorses().add(new Horse("House2", 3.0d, 0.0d));
        game.getHorses().add(new Horse("House3", 3.0d, 0.0d));

        try
        {
            game.run();
        }
        catch (InterruptedException e)
        {/*NOP*/}

        game.printWinner();
    }

    public ArrayList<Horse> getHorses() {
        return horses;
    }

    public void move() {
        for (Horse horse : horses) {
            horse.move();
        }
    }

    public void print() {
        for (Horse horse : horses) {
            horse.print();
        }
        System.out.println();
        System.out.println();
    }

    public void run() throws InterruptedException
    {
        for (int i = 0; i < 100; i++)
        {
            move();
            print();
            Thread.sleep(200);
        }
    }

    public Horse getWinner() {
        Horse winner = horses.get(0);
        for (Horse horse : horses) {
            if (horse.getDistance() > winner.getDistance()) winner = horse;
        }
        return winner;
    }

    public void printWinner() {
        StringBuilder sb = new StringBuilder("Winner is ");
        sb.append(getWinner().getName());
        sb.append("!");
        System.out.println(sb.toString());
    }
}
