package com.comp2042.panel;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Displays when the player successfully completes all defined levels.
 * It contains the "YOU WIN!" text styled using the CSS class "gameOverStyle".
 */
public class CongratulationsPanel extends BorderPane {

    /**
     * Constructs the panel, initializes the "YOU WIN!" label.
     */
    public CongratulationsPanel() {
        final Label congratulationsLabel = new Label("  YOU WIN!  ");
        congratulationsLabel.getStyleClass().add("gameOverStyle");
        setCenter(congratulationsLabel);
    }
}
