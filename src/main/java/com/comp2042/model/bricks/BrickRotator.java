package com.comp2042.model.bricks;

import com.comp2042.model.bricks.shape.Brick;
import com.comp2042.model.bricks.shape.NextShapeInfo;
import com.comp2042.model.bricks.shape.PropBrick;

/**
 * Manages the rotation state of the currently falling brick.
 * It tracks the current rotation index and calculates the matrix for the next rotation for collision checking.
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Calculates the matrix and index for the next rotation.
     * @return {@code NextShapeInfo} object containing the shape matrix and index of the next rotation.
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the matrix representing the current rotation state of the active brick.
     * @return matrix of the current brick shape
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current shape.
     * @param currentShape index corresponding to the new rotation state.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets a new brick as a falling brick and resets its rotation index to 0.
     * @param brick a new {@code Brick} object
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Checks whether the brick is prop brick or not.
     * @return {@code true} if it is prop brick, else {@code false}
     */
    public boolean isPropBrick() {
        if (brick instanceof PropBrick) {
            return true;
        }
        return false;
    }
}
