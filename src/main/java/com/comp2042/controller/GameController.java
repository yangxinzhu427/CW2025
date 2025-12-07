package com.comp2042.controller;

import com.comp2042.model.event.InputEventListener;
import com.comp2042.model.event.MoveEvent;
import com.comp2042.model.board.Board;
import com.comp2042.model.board.SimpleBoard;
import com.comp2042.model.game.data.ClearRow;
import com.comp2042.model.game.data.DownData;
import com.comp2042.model.game.data.ViewData;
import javafx.beans.property.IntegerProperty;

import static com.comp2042.model.game.data.Level.LEVEL_UP_SCORE;
import static com.comp2042.model.game.data.Level.MAX_LEVEL;


/**
 * Handles the game logic between GuiController and Board.
 * It implements {@code InputEventListener} to respond to user and timer inputs.
 */
public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;

    /**
     * Constructs the GameController and sets up the initial game state.
     * It connectss the GUI controller and initializes the board, score, and level bindings.
     * @param c {@code GuiController} to manage view layer
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLevel(board.getLevel().levelProperty());
    }

    /**
     * Checks whether the brick can drop or not.
     * If the current brick can't move, then check if there have any rows.
     * If there have completed rows then clear them and add score.
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;

        IntegerProperty scoreProp = board.getScore().scoreProperty();
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            viewGuiController.setFinalScore(scoreProp.get());
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            board.getScore().add(1);

            viewGuiController.setFinalScore(scoreProp.get());
        }
        checkAndAdvanceLevel();
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Handles left movement event
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Handles right movement event
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Handles rotation event
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }
    /**
     * Handles hard drop event
     * @param event a {@code Movement} object containg source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    @Override
    public DownData onHardDropEvent(MoveEvent event) {
        board.hardDrop(); //

        IntegerProperty scoreProp = board.getScore().scoreProperty();

        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();

        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
        }

        viewGuiController.setFinalScore(scoreProp.get());
        if (board.createNewBrick()) {
            viewGuiController.gameOver();
        }
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        checkAndAdvanceLevel();
        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Checks whether leveled up or not.
     *  If the score reaches a specific number, then clear all bricks and make bricks fall faster.
     */
    private void checkAndAdvanceLevel() {
        if (board.getLevel().getLevel() > MAX_LEVEL) {
            viewGuiController.completeLevels();
            return;
        }
        int score = board.getScore().scoreProperty().get();
        int targetScore = LEVEL_UP_SCORE[board.getLevel().getLevel()-1];
        if (board.getLevel().getLevel() <= MAX_LEVEL && score >= targetScore) {
            board.getLevel().nextLevel();

            if (board.getLevel().getLevel() > MAX_LEVEL) {
                board.getLevel().setLevel(MAX_LEVEL);
                viewGuiController.completeLevels();
                return;
            }

            board.nextGame();
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            viewGuiController.updateGameSpeed(board.getLevel().getLevel()-1);
        }
    }

    /** Creates new game. */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
