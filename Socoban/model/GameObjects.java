package com.javarush.test.level34.lesson15.big01.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Segiy on 16.03.2016.
 */
public class GameObjects {
    private Set<Wall> walls;
    private Set<Box> boxes;
    private Set<Home> homes;
    Player player;

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Player player) {
        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.player = player;
    }

    public Set<Wall> getWalls() {
        return walls;
    }

    public Set<Box> getBoxes() {
        return boxes;
    }

    public Set<Home> getHomes() {
        return homes;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<GameObject> getAll() {
        Set<GameObject> result = new HashSet<>();
        result.addAll(walls);
        result.addAll(boxes);
        result.addAll(homes);
        result.add(player);

        return result;
    }
}
