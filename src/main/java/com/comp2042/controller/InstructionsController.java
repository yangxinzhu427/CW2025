package com.comp2042.controller;

import com.comp2042.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles the instruction panel.
 * Manages back menu method.
 */
public class InstructionsController {

    private Stage primaryStage;

    /**
     * Sets the primary stage from the main menu controller.
     * @param stage the primary {@code Stage} object
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }


    /**
     * Handles the Menu button click.
     * Reloads the mainMenuLayout.fxml scene onto the primary stage.
     */
    @FXML
    public void backToMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("mainMenuLayout.fxml"));
            Parent root = fxmlLoader.load();

            // Set the main menu scene
            primaryStage.setScene(new Scene(root, 350, 510));
            primaryStage.setTitle("TetrisJFX - Main Menu");

            MainMenuController menuController = fxmlLoader.getController();
            menuController.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}