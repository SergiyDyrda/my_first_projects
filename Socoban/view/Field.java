package com.javarush.test.level34.lesson15.big01.view;

import com.javarush.test.level34.lesson15.big01.controller.EventListener;
import com.javarush.test.level34.lesson15.big01.model.Direction;
import com.javarush.test.level34.lesson15.big01.model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Segiy on 16.03.2016.
 */
public class Field extends JPanel {
    private EventListener eventListener;
    private View view;

    public Field(View view) {
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        view.addKeyListener(keyHandler);
        view.setFocusable(true);
    }

    public void paint(Graphics g) {
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(Color.BLACK);

        for (GameObject gameObject : view.getGameObjects().getAll()) {
            gameObject.draw(g);
        }

    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT :
                    eventListener.move(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT :
                    eventListener.move(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP :
                    eventListener.move(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN :
                    eventListener.move(Direction.DOWN);
                    break;
                case KeyEvent.VK_R :
                    eventListener.restart();
                    break;
            }
        }
    }
}
