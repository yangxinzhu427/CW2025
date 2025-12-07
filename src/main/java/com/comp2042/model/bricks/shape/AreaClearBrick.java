package com.comp2042.model.bricks.shape;

import com.comp2042.model.game.data.MatrixOperations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a special brick clears a defined area around its center point when the row including the brick is completed
 * It implements the {@code PropBrick} interface.
 */
public class AreaClearBrick implements PropBrick{

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Initializes the Area Clear Brick.
     * '8' represents the block color code.
     */
    public AreaClearBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 8, 8, 0},
                {0, 8, 8, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets a deep copy of the list including all rotation matrices for this brick shape.
     * @return a {@code List} of matrices
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

    /**
     * Executes the area clear effect when the row including this brick is completed.
     * If other prop bricks are found in the area, their effects are recursively triggered.
     * @param newMatrix game board matrix to modify
     * @param brickPosition center {@code Point} (x, y) where the area clear brick landed
     */
    @Override
    public void effect(int[][] newMatrix, Point brickPosition) {
        int x = brickPosition.x;
        int y = brickPosition.y;
        for (int i = x-2;i <= x+2;i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (i < 0 || i >= newMatrix[0].length) {
                    continue;
                }
                if (j < 0 || j >= newMatrix.length) {
                    continue;
                }
                PropBrick propBrick = RandomBrickGenerator.getPropBrick(newMatrix[j][i]);
                if (propBrick != null && !(i == x && j == y)) {
                    Point point = new Point(i, j);
                    propBrick.effect(newMatrix, point);
                }
                newMatrix[j][i] = 0;
            }
        }
    }

    /**
     * Gets the color code representing area clear brick
     * @return the color code '8' for the area clear brick
     */
    @Override
    public int PropBrickValue() {
        return 8;
    }

    /**
     * Gets the score awarded specifically for clearing area clear brick.
     * @return the score value
     */
    @Override
    public int getPropScore() {
        return 50;
    }
}