package com.comp2042.application;

import com.comp2042.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The main entry for Tetris game.
 * It sets up the primary JavaFX stage and loads the initial main menu layout.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("mainMenuLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();

        MainMenuController menuController = fxmlLoader.getController();
        menuController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 350, 510);

        scene.getStylesheets().add(getClass().getResource("/window_style.css").toExternalForm());

        primaryStage.setTitle("TetrisJFX - Main Menu");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
