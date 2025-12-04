package com.comp2042.model.board;

import com.comp2042.model.game.data.ClearRow;
import com.comp2042.model.game.data.Level;
import com.comp2042.model.game.data.Score;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    void hardDrop();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();

    Level getLevel();

    void nextGame();
}
