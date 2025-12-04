package com.comp2042.controller;

import com.comp2042.model.event.MoveEvent;
import com.comp2042.model.board.Board;
import com.comp2042.model.board.SimpleBoard;
import com.comp2042.model.game.data.ClearRow;
import com.comp2042.model.game.data.DownData;
import com.comp2042.model.board.ViewData;
import javafx.beans.property.IntegerProperty;

import static com.comp2042.model.game.data.Level.LEVEL_UP_SCORE;
import static com.comp2042.model.game.data.Level.MAX_LEVEL;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private IntegerProperty scoreProperty;
    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.bindLevel(board.getLevel().levelProperty());
    }

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

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

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


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
