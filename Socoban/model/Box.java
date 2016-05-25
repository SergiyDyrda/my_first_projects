package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

/**
 * Created by Segiy on 16.03.2016.
 */
public class Box extends CollisionObject implements Movable {

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        int newX = getX()-getWidth() / 2;
        int newY = getY()-getHeight() / 2;
        graphics.fillRect(newX, newY, getWidth(), getHeight());
        graphics.setColor(Color.ORANGE);
    }

    @Override
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }
}
