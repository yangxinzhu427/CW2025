package com.comp2042.model.game.data;

import com.comp2042.model.bricks.shape.PropBrick;
import com.comp2042.model.bricks.shape.RandomBrickGenerator;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains static methods for handling 2D matrices.
 * Provides collision detection, merging, copying, and row clearing in Tetris.
 */
public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

    /**
     * Checks collision between the current falling brick and game board matrix at (x, y).
     * @param matrix the game board matrix
     * @param brick current falling brick's shape
     * @param x x position of the brick
     * @param y y position of the brick
     * @return {@code true} if collision occurs, else {@code false}
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a target coordinate is outside the bounds of the game matrix.
     * @param matrix the game board matrix
     * @param targetX x coordinate
     * @param targetY y coordinate
     * @return {@code true} if the coordinates are out of bounds, else {@code false}
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Creates a deep copy of a 2D matrix.
     * @param original the matrix to be copied
     * @return a new 2D matrix with original values
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges the currently falling brick into a copy of  game board matrix.
     * @param filledFields original game board matrix
     * @param brick landed brick's shape
     * @param x final X position of the landed brick
     * @param y final Y position of the landed brick
     * @return game board with the new brick merged into it
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Checks the game matrix for any complete rows, removes them, and drops the blocks above.
     * Calculates the score bonus based on the number of clearing lines.
     * @param matrix current game board matrix
     * @return a {@code ClearRow} object containing the number of removed lines, new matrix, and score bonus
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();
        List<Prop> props = new ArrayList<>();
        int sumPropScore = 0;

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                for (int i1 = 0; i1 < tmpRow.length; i1++) {
                    PropBrick propBrick = RandomBrickGenerator.getPropBrick(tmpRow[i1]);
                    if (propBrick != null) {
                        Prop prop = new Prop(new Point(i1, i), propBrick.PropBrickValue());
                        props.add(prop);
                        sumPropScore = sumPropScore + propBrick.getPropScore();
                    }
                }
                clearedRows.add(i);
            } else {
                newRows.add(matrix[i]);
            }
        }
        if (!props.isEmpty()) {
            for (Prop prop : props) {
                RandomBrickGenerator.getPropBrick(prop.getType()).effect(matrix, prop.getPoint());
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                System.arraycopy(row, 0, tmp[i], 0, row.length);
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size() + sumPropScore;
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Performs a deep copy of a list containing 2D matrices.
     * @param list the list of 2D integer matrices
     * @return a new {@code List<int[][]>} where all internal matrices are also deep copies.
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
