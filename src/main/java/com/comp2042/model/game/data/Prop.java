package com.comp2042.model.game.data;

import java.awt.*;

/**
 * Contains methods for handling the position and type of prop brick
 */
public class Prop {

    private final Point point;
    private final int type;


    /**
     * Constructs prop brick.
     * @param point position of prop brick
     * @param type type of brick
     */
    public Prop(Point point, int type) {
        this.point = point;
        this.type = type;
    }

    /**
     * Gets the position of prop brick.
     * @return point of brick
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Gets type of prop brick.
     * @return type of prop brick
     */
    public int getType() {
        return type;
    }

}