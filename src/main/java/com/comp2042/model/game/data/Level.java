package com.comp2042.model.game.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the current game level and provides constants for level thresholds and speeds.
 * Uses an {@code IntegerProperty} for binding to display the level in the GUI.
 */
public class Level {
    public static double[] LEVEL_SPEEDS = {400, 300, 200, 150, 100};

    public static final int[] LEVEL_UP_SCORE = {200,450,700,1000,1200};

    public static final int MAX_LEVEL = LEVEL_UP_SCORE.length;

    private final IntegerProperty level = new SimpleIntegerProperty(1);

    /**
     * Gets the observable property for the current level.
     * @return a {@code IntegerProperty} object that can be bound to the GUI element
     */
    public IntegerProperty levelProperty() {
        return level;
    }

    /**
     * Sets the current game level.
     * @param i  new level number.
     */
    public void setLevel(int i){
        level.setValue(i);
    }

    /**
     * Gets the current game level number.
     * @return value of the current level
     */
    public int getLevel(){
        return level.getValue();
    }

    /**
     * Increments the current game level by one.
     */
    public void nextLevel(){
        level.setValue(level.getValue() + 1);
    }

}
