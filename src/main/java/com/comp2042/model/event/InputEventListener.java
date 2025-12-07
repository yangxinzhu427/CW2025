package com.comp2042.model.event;

import com.comp2042.model.game.data.DownData;
import com.comp2042.model.game.data.ViewData;


/**
 * Interface for handling input event using in controller.
 * It defines events on falling bricks.
 */
public interface InputEventListener {

    /**
     * Handles soft drop event.
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles left movement event.
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles right movement event.
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles rotation event.
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Handles hard drop event.
     * @param event a {@code MoveEvent} object containing source of event
     * @return a {@code DownData} object containing row clear results and updated view data
     */
    DownData onHardDropEvent(MoveEvent event);

    /** Creates new game. */
    void createNewGame();
}
