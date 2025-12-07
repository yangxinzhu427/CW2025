package com.comp2042.model.bricks.shape;

import com.comp2042.model.game.data.MatrixOperations;

/**
 * Handles the resulting shape matrix and its position index after a rotation.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructs a NextShapeInfo object.
     * @param shape the 2D matrix of the shape after rotation
     * @param position the position index in brick's shape matrix
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets the shape matrix of the next rotation state.
     * @return a deep copy of the integer matrix
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the index of the next rotation state.
     * @return position index
     */
    public int getPosition() {
        return position;
    }
}
