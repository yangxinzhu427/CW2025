package com.comp2042.model.game.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Level {
    public static double[] LEVEL_SPEEDS = {400, 300, 200, 150, 100};

    public static final int[] LEVEL_UP_SCORE = {150,400,600,800,1000};

    public static final int MAX_LEVEL = LEVEL_UP_SCORE.length;

    private final IntegerProperty level = new SimpleIntegerProperty(1);

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int i){
        level.setValue(i);
    }

    public int getLevel(){
        return level.getValue();
    }

    public void nextLevel(){
        level.setValue(level.getValue() + 1);
    }

    public void reset() {
        level.setValue(1);
    }
}
