package com.comp2042.model.game.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents current game score.
 * Uses an {@code IntegerProperty} for binding scores to GUI when scores changed.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Gets property for the current score.
     * @return a {@code IntegerProperty} that can be bound to a GUI element
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds a specified amount to the current score.
     * @param i the amount of score points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the current score back to zero.
     */
    public void reset() {
        score.setValue(0);
    }
}
