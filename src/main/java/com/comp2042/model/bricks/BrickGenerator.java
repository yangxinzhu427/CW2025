package com.comp2042.model.bricks;

import com.comp2042.model.bricks.shape.Brick;

/**
 * Defines the contract for generating and previewing brick shapes.
 */
public interface BrickGenerator {

    /**
     * Gets the next brick from the front of the queue and removes it.
     * If the queue size drops below 2 after removal, a new random brick is generated and added to the back.
     * @return The {@code Brick} object that is falling
     */
    Brick getBrick();

    /**
     * Gets the brick that is next in line to fall without removing it from the queue.
     * @return the {@code Brick} object at the head of the preview queue
     */
    Brick getNextBrick();
}
