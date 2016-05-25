package com.javarush.test.level34.lesson15.big01.model;

import com.javarush.test.level34.lesson15.big01.controller.EventListener;

import java.nio.file.Paths;

/**
 * Created by Segiy on 16.03.2016.
 */
public class Model {
    private static final String levelsPath = "D:\\IdeaProjects\\JavaRushHomeWork\\src\\com\\javarush\\test\\level34\\lesson15\\LogParser\\res\\levels.txt";
    private GameObjects gameObjects;
    private EventListener eventListener;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get(levelsPath));
    public static int FIELD_SELL_SIZE = 20;

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }


    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restart();
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player, direction)) {
            return;
        }
        if (checkBoxCollision(direction)) {
            return;
        }

        switch (direction) {
            case LEFT:
                player.move(-FIELD_SELL_SIZE, 0);
                break;
            case UP:
                player.move(0, -FIELD_SELL_SIZE);
                break;
            case RIGHT:
                player.move(FIELD_SELL_SIZE, 0);
                break;
            case DOWN:
                player.move(0, FIELD_SELL_SIZE);
        }

        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkBoxCollision(Direction direction) {
        Player player = gameObjects.getPlayer();
        GameObject barrier = null;
        for (GameObject gameObject : gameObjects.getAll()) {
            if (!(gameObject instanceof Player) && !(gameObject instanceof Home) && player.isCollision(gameObject, direction))
                barrier = gameObject;
        }
        if (barrier == null) return false;
        if (barrier instanceof Box) {
            Box box = (Box) barrier;
            if (checkWallCollision(box, direction)) return true;
            for (Box b : gameObjects.getBoxes()) {
                if (box.isCollision(b, direction)) return true;
            }
            switch (direction) {
                case LEFT:
                    box.move(-FIELD_SELL_SIZE, 0);
                    break;
                case RIGHT:
                    box.move(FIELD_SELL_SIZE, 0);
                    break;
                case UP:
                    box.move(0, -FIELD_SELL_SIZE);
                    break;
                case DOWN:
                    box.move(0, FIELD_SELL_SIZE);
            }
        }
        return false;
    }

    public void checkCompletion() {
        int count = 0;

        for (Home home : gameObjects.getHomes()) {
            for (Box box : gameObjects.getBoxes()) {
                if (box.getX() == home.getX() && box.getY() == home.getY())
                    count++;
            }
        }

        if (count == gameObjects.getHomes().size())
            eventListener.levelCompleted(currentLevel);
    }
}
