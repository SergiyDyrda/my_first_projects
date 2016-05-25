package com.javarush.test.level34.lesson15.big01.model;

import java.awt.*;

/**
 * Created by Segiy on 16.03.2016.
 */
public class Wall extends CollisionObject {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        int newX = getX()-getWidth() / 2;
        int newY = getY()-getHeight() / 2;
        graphics.drawRect(newX, newY, getWidth(), getHeight());
        graphics.fillRect(newX, newY, getWidth(), getHeight());
        graphics.setColor(Color.GRAY);
    }
}
