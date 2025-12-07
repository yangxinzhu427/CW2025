package com.comp2042.model.bricks.shape;

import java.util.List;

public interface Brick {

    /**
     * Gets a deep copy of the list including all rotation matrices for this brick shape.
     * @return a {@code List} of matrices
     */
    List<int[][]> getShapeMatrix();
}
