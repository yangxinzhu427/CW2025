package com.comp2042.model.bricks;

import com.comp2042.model.bricks.shape.Brick;

public interface BrickGenerator {

    Brick getBrick();

    Brick getNextBrick();
}
