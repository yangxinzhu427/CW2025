package com.comp2042.model.game.data;


/**
 * Passes the results of a down movement from game controller to GUI controller.
 * Encapsulates whether any rows are cleared and the new viewdata of bricks.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Constructs a DownData object.
     * @param clearRow result of the row clearing operation
     * @param viewData updated view data for the falling and next bricks
     * */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets the result of the row clearing operation.
     * @return a {@code ClearRow} object containing number of removed lines, score bonus and updated game board matrix after clearing bricks
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the latest view data for the falling and next bricks.
     * @return a {@code ViewData} object
     */
    public ViewData getViewData() {
        return viewData;
    }
}
