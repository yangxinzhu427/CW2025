package com.comp2042.model.board;

import com.comp2042.model.game.data.*;
import com.comp2042.model.bricks.BrickGenerator;
import com.comp2042.model.bricks.BrickRotator;
import com.comp2042.model.bricks.shape.Brick;
import com.comp2042.model.bricks.shape.NextShapeInfo;
import com.comp2042.model.bricks.shape.RandomBrickGenerator;

import java.awt.*;

/** simple board implements board */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    private final Level level;

    /** Constructs a new SimpleBoard.
     * @param height number of rows in the game board matrix
     * @param width number of columns in the game board matrix
     */
    public SimpleBoard(int height, int width) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[height][width];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
        level = new Level();
    }

    /**
     * Moves brick down when no collision with the floor or other fixed blocks.
     * @return {@code true} if the brick moved down successfully else {@code false}
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Moves brick left by one unit.
     * @return {@code true} if the brick moved left successfully else {@code false}
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     Moves brick right by one unit.
     * @return {@code true} if the brick moved right successfully else {@code false}
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     Rotates brick left.
     * @return {@code true} if the brick rotate left successfully else {@code false}
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }
    /**
     * Handles hard drop.
     */
    @Override
    public void hardDrop() {
        while (moveBrickDown()) {}
    }

    /**
     * Creates and initialize a new brick.
     * The brick is generated randomly and placed at the top-center position.
     * @return {@code true} if the new brick immediately conflicts with bricks, else {@code false}
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point((width-currentBrick.getShapeMatrix().get(0).length) / 2, 0);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Gets the current state of the game board matrix.
     * @return 2D array which represents the game board
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     Gets data to render the current game.
     * @return a {@code ViewData} object
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0),brickRotator.isPropBrick());
    }
    /** Merge the current brick into game board matrix when the brick lands. */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Checks completed rows in game matrix.
     * If there is completed rows then clear all the rows.
     * @return a {@code ClearRow} object containing the number of removed rows, the new matrix, and the score bonus
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;
    }

    /**
     * Gets current game score.
     * @return a {@code Score} object
     */
    @Override
    public Score getScore() {
        return score;
    }

    /** Resets game matrix to start a new game. */
    @Override
    public void newGame() {
        currentGameMatrix = new int[height][width];
        score.reset();
        createNewBrick();
    }

    /**
     * Gets the current level object.
     * @return a {@code Level} object
     */
    @Override
    public Level getLevel() {
        return level;
    }

    /** Resets game matrix to advance level. */
    @Override
    public void nextGame() {
        currentGameMatrix = new int[height][width];
        createNewBrick();
    }
}