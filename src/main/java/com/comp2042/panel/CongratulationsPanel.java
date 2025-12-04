package com.comp2042.panel;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class CongratulationsPanel extends BorderPane {

    public CongratulationsPanel() {
        final Label congratulationsLabel = new Label("  YOU WIN!  ");
        congratulationsLabel.getStyleClass().add("gameOverStyle");
        setCenter(congratulationsLabel);
    }
}
