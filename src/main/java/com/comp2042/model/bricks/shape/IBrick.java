package com.comp2042.model.bricks.shape;

import com.comp2042.model.game.data.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class IBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Initializes the I Brick.
     * '1' represents the block color code.
     */
    public IBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
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

}
