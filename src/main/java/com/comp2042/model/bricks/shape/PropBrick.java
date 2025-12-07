package com.comp2042.model.bricks.shape;

import java.awt.*;

/**
 * Interface for area clear brick.
 */
public interface PropBrick extends Brick {
    /**
     * Executes the area clear effect when the row including this brick is completed.
     * If other prop bricks are found in the area, their effects are recursively triggered.
     * @param newMatrix game board matrix to modify
     * @param brickPosition center {@code Point} (x, y) where the area clear brick landed
     */
    void effect(int[][] newMatrix, Point brickPosition);

    /**
     * Gets the color code representing area clear brick
     * @return the color code for the area clear brick
     */
    int PropBrickValue();

    /**
     * Gets the score awarded specifically for clearing area clear brick.
     * @return the score value
     */
    int getPropScore();
}