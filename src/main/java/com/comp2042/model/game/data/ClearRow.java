package com.comp2042.model.game.data;


/**
 * Encapsulates result of row clearing on the game board.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * Constructs a ClearRow object.
     * @param linesRemoved total number of cleared rows in this operation
     * @param newMatrix the updated game board matrix after clearing bricks
     * @param scoreBonus bonus awarded for clearing rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets number of the lines that are removed.
     * @return number of removed lines
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets new game board matrix after clearing rows.
     * @return a deep copy of updated game board matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets score bonus awarded for clearing rows.
     * @return value of score bonus
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
