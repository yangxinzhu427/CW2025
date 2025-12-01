package com.comp2042.controller;

import com.comp2042.model.game.data.DownData;
import com.comp2042.model.event.MoveEvent;
import com.comp2042.model.board.ViewData;

public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    DownData onHardDropEvent(MoveEvent event);

    void createNewGame();
}
