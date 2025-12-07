package com.comp2042.controller;

import com.comp2042.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles user interactions in main menu.
 */
public class MainMenuController {
        private Stage primaryStage;

    /**
     * Primary {@code Stage} of application.
     * Display scenes.
     * @param stage
     */
    public void setPrimaryStage(Stage stage) {
            this.primaryStage = stage;
        }

    /**
     * Handles start game event.
     */
    @FXML
        public void startGame() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("gameLayout.fxml"));
                Parent gameRoot = fxmlLoader.load();
                GuiController guiController = fxmlLoader.getController();

                new GameController(guiController);

                primaryStage.setScene(new Scene(gameRoot, 350, 510));
                primaryStage.setTitle("TetrisJFX Game");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Loads the instruction panel and display the game tutorial.
     */
    @FXML
    public void showInstructions() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("instructions.fxml"));
            Parent instructionRoot = fxmlLoader.load();

            InstructionsController instructionsController = fxmlLoader.getController();
            instructionsController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(instructionRoot, 350, 510));
            primaryStage.setTitle("TetrisJFX - Instructions");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Exit button click.
     * Closes the primary stage.
     */
    @FXML
    public void exitGame() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }
}
