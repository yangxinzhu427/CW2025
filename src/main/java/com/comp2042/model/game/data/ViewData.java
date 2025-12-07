package com.comp2042.model.game.data;

/**
 * Encapsulates data for rendering.
 * Contains the falling brick's position, shape, and the next shape preview.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final boolean isProp;

    /**
     * Constructs a ViewData object.
     * @param brickData the matrix that represents the shape of the current brick
     * @param xPosition the column index of the falling brick on the game board
     * @param yPosition the row index of the falling brick on the game board
     * @param nextBrickData the matrix that represents the shape of the next brick
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, boolean isProp) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.isProp = false;
    }

    /**
     * Gets the shape matrix of the currently falling brick.
     * A copy is returned to prevent external modification of the internal data.
     * @return a deep copy of the matrix that represents the brick's shape
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the x position of the currently falling brick.
     * @return the column index where the brick is located
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the y position of the currently falling brick.
     * @return the row index where the brick is located
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets the shape matrix of the next brick to fall.
     * @return a deep copy of the matrix that represents the next brick's shape
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Checks whether the current falling brick is prop brick or not.
     * @return {@code true} if it is prop brick, else {@code false}
     */
    public boolean isProp() {
        return isProp;
    }
}
