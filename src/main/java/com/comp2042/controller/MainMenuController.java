package com.comp2042.controller;

import com.comp2042.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

/** main menu controller
 * start main menu */
public class MainMenuController {
        private Stage primaryStage;

        public void setPrimaryStage(Stage stage) {
            this.primaryStage = stage;
        }

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

}
