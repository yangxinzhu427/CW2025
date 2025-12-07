package com.comp2042.model.bricks.shape;

import com.comp2042.model.game.data.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

final class OBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Initializes the O Brick.
     * '4' represents the block color code.
     */
    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
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

}
