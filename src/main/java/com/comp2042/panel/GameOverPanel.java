package com.comp2042.panel;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Displays when the game ends.
 * It contains the "GAME OVER" text styled using the CSS class "gameOverStyle".
 */
public class GameOverPanel extends BorderPane {

    /**
     * Constructs the panel, initializes the "GAME OVER" label.
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
