package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

/**
 * Created by Segiy on 16.03.2016.
 */
public class Player extends CollisionObject implements Movable {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.YELLOW);
        int newX = getX()-getWidth() / 2;
        int newY = getY()-getHeight() / 2;
        graphics.drawOval(newX, newY, getWidth(), getHeight());
        graphics.fillOval(newX, newY, getWidth(), getHeight());
    }

    @Override
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }
}
