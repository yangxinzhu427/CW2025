package com.comp2042.model.board;

import com.comp2042.model.game.data.ClearRow;
import com.comp2042.model.game.data.Level;
import com.comp2042.model.game.data.Score;
import com.comp2042.model.game.data.ViewData;

/**
 * Interface for a game board model.
 * It defines brick movement, collision detection, state updates, scoring, and level management.
 */
public interface Board {

    /**
     * Moves brick down when no collision with the floor or other fixed blocks.
     * @return {@code true} if the brick moved down successfully else {@code false}
     */
    boolean moveBrickDown();

    /**
     * Moves brick left by one unit.
     * @return {@code true} if the brick moved left successfully else {@code false}
     */
    boolean moveBrickLeft();

    /**
     *  Moves brick right by one unit.
     * @return {@code true} if the brick moved left successfully else {@code false}
     */
    boolean moveBrickRight();

    /**
     * Moves brick left by one unit.
     * @return {@code true} if the brick moved left successfully else {@code false}
     */
    boolean rotateLeftBrick();

    /**
     * Handles hard drop.
     */
    void hardDrop();

    /**
     * Creates and initialize a new brick.
     * @return {@code true} if the new brick immediately conflicts with bricks, else {@code false}
     */
    boolean createNewBrick();

    /**
     * Gets the current state of the game board matrix.
     * @return 2D array which represents the game board
     */
    int[][] getBoardMatrix();

    /**
     Gets data to render the current game.
     * @return a {@code ViewData} object
     */
    ViewData getViewData();

    /** Merge the current brick into game board matrix when the brick lands. */
    void mergeBrickToBackground();

    /**
     * Checks completed rows in game matrix.
     * If there is completed rows then clear all the rows.
     * @return a {@code ClearRow} object containing the number of removed rows, the new matrix, and the score bonus
     */
    ClearRow clearRows();

    /**
     * Gets current game score.
     * @return a {@code Score} object
     */
    Score getScore();

    /** Resets game matrix to start a new game. */
    void newGame();

    /**
     * Gets the current level object.
     * @return a {@code Level} object
     */
    Level getLevel();

    /** Resets game matrix to advance level. */
    void nextGame();
}
