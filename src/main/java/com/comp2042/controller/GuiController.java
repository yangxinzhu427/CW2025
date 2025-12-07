package com.comp2042.controller;

import com.comp2042.model.event.EventSource;
import com.comp2042.model.event.EventType;
import com.comp2042.model.event.InputEventListener;
import com.comp2042.model.event.MoveEvent;
import com.comp2042.model.game.data.DownData;
import com.comp2042.model.game.data.ViewData;
import com.comp2042.panel.GameOverPanel;
import com.comp2042.panel.NotificationPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.net.URL;
import java.util.ResourceBundle;
import com.comp2042.model.game.data.Level;
import com.comp2042.panel.CongratulationsPanel;

/**
 * Handles GUI, user input, rendering of the board and bricks, game states, and score display.
 */
public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane nextBrickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    @FXML
    private Text scoreText;

    @FXML
    private Text highScoreText;

    @FXML
    private Text levelText;

    @FXML
    private CongratulationsPanel congratulationsPanel;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @FXML
    private Text pauseStyle;

    private Rectangle[][] nextBrickRectangles;

    private static final String HIGHSCORE_FILE = "highscore.txt";

    private int currentHighScore = 0;

    private int finalScore = 0;

    /**
     * Initializes the controller after the FXML has been loaded.
     * Sets up fonts, keyboard event handlers, next brick panel, and initial UI.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {

            /**
             * Handles keys for game control including pause, move, hard drop and new game.
             * @param keyEvent a {@code KeyEvent} object for triggering actions
             */
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.P && isGameOver.getValue() == Boolean.FALSE) {
                    pauseGame();
                    keyEvent.consume();
                    return;
                }

                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.SPACE) {
                        hardDrop(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });

        // brick panel initialization
        nextBrickRectangles = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                nextBrickRectangles[i][j] = rectangle;
                nextBrickPanel.add(rectangle, j, i);
            }
        }

        if (highScoreText != null) {
            highScoreText.setVisible(false);
        }

        gameOverPanel.setVisible(false);
        congratulationsPanel.setVisible(false);

        loadHighScore();
        HighScoreDisplay();

        // text initialization
        pauseStyle = new Text("PAUSED");
        pauseStyle.getStyleClass().add("titleStyle");
        pauseStyle.setX(30);
        pauseStyle.setY(100);
        pauseStyle.setVisible(false);
        groupNotification.getChildren().add(pauseStyle);

        Text nextBrickText = new Text("Next Shape: ");
        nextBrickText.getStyleClass().add("textStyle");
        nextBrickText.setX(220);
        nextBrickText.setY(-70);
        groupNotification.getChildren().add(nextBrickText);

        scoreText = new Text("Score: 0");
        scoreText.getStyleClass().add("textStyle");
        scoreText.setX(220);
        scoreText.setY(-140);
        groupNotification.getChildren().add(scoreText);

        levelText = new Text("LEVEL: 1");
        levelText.getStyleClass().add("textStyle");
        levelText.setX(220);
        levelText.setY(40);
        groupNotification.getChildren().add(levelText);

    }

    /**
     * Sets up the initial game view.
     * Initializes the display matrices for the board and the current brick.
     * @param boardMatrix the initial state of the game board matrix
     * @param brick initial {@code ViewData} object for the first falling brick
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        // initialize the display rectangles for the current brick
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        // start game timer with initial level speed
        updateGameSpeed(1);
    }

    /**
     * Gets color from javafx paint color.
     * @param i color code
     * @return JavaFX {@code Paint} object
     */
    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            case 8:
                returnPaint = Color.PINK;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }

    /**
     * Updates the position and appearance of the current brick.
     * @param brick latest {@code ViewData} object containing brick's shape and position
     */
    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            updateNextBrick(brick);
        }
    }

    /** Updates the game background.
     * @param board latest game board matrix.
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Sets color and arc for a rectangle.
     * @param color color code
     * @param rectangle {@code Rectangle} object to update
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * Handles soft drop event.
     * @param event a {@code MoveEvent} object containg source of event
     */
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    /**
     * Sets eventListener.
     * @param eventListener a {@code InputEventListener} object handling the game logic
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds score to integer property.
     * @param integerProperty score property
     */
    public void bindScore(IntegerProperty integerProperty) {
        scoreText.textProperty().bind(
                integerProperty.asString("SCORE: %d")
        );
    }

    /** Shows game over panel and stop the game. */
    public void gameOver() {
        timeLine.stop();
        updateScore(this.finalScore);

        if (highScoreText != null) {
            if (gameOverPanel.getChildren().contains(highScoreText) == false) {
                gameOverPanel.getChildren().add(highScoreText);
            }
            highScoreText.setX(50);
            highScoreText.setY(80);
            highScoreText.setVisible(true);
        }

        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /** Creates a new game.*/
    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    /** Pauses game. */
    public void pauseGame() {
        if (isGameOver.getValue() == Boolean.TRUE) {
            return;
        }

        if (isPause.getValue() == Boolean.FALSE) {
            isPause.setValue(Boolean.TRUE);
            timeLine.pause();
            pauseStyle.setVisible(true);
        } else {
            isPause.setValue(Boolean.FALSE);
            timeLine.play();
            pauseStyle.setVisible(false);
            gamePanel.requestFocus();
        }
    }

    /**
     * Handles hard drop event.
     * @param event a {@code MoveEvent} object containing source of event
     */
    private void hardDrop(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onHardDropEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    /**
     * Updates the next brick.
     * @param brick  latest {@code ViewData} object containing the next brick's shape
     */
    private void updateNextBrick(ViewData brick) {
        int[][] nextBrickData = brick.getNextBrickData();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i < nextBrickData.length && j < nextBrickData[i].length) {
                    nextBrickRectangles[i][j].setFill(getFillColor(nextBrickData[i][j]));
                } else {
                    nextBrickRectangles[i][j].setFill(Color.TRANSPARENT);
                }
                nextBrickRectangles[i][j].setArcHeight(9);
                nextBrickRectangles[i][j].setArcWidth(9);
            }
        }
    }

    /** Loads high score to current high score. */
    private void loadHighScore() {
        File file = new File(HIGHSCORE_FILE);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextInt()) {
                    currentHighScore = scanner.nextInt();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates high score.
     * @param finalScore score achieved in the completed game
     */
    private void updateScore(int finalScore) {
        if (finalScore > currentHighScore) {
            saveHighScore(finalScore);
            HighScoreDisplay();
        }
    }

    /** Displays high score */
    private void HighScoreDisplay() {
        highScoreText.setText("HIGH SCORE: " + currentHighScore);
    }

    /**
     * Saves high score to an external file.
     * @param newScore a new high score to save
     */
    private void saveHighScore(int newScore) {
        try (FileWriter writer = new FileWriter(HIGHSCORE_FILE)) {
            writer.write(String.valueOf(newScore));
            currentHighScore = newScore;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the final score achieved in the game.
     * @param score the final score
     */
    public void setFinalScore(int score) {
        this.finalScore = score;
    }

    /**
     * Binds level value to integer property.
     * @param integerProperty level property
     */
    public void bindLevel(IntegerProperty integerProperty) {
        levelText.textProperty().bind(
                integerProperty.asString("LEVEL: %d")
        );
    }

    /**
     * Shows congratulations panel when all levels are completed.
     * It also checks and updates the high score.
     */
    public void completeLevels() {
        timeLine.stop();
        updateScore(this.finalScore);
        if (highScoreText != null) {
            if (!gameOverPanel.getChildren().contains(highScoreText)) {
                gameOverPanel.getChildren().add(highScoreText);
            }
            highScoreText.setX(50);
            highScoreText.setY(80);
            highScoreText.setVisible(true);
        }
        congratulationsPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Stops the current game timer and restart it with a new speed according to the current level.
     * @param level the current game level
     */
    public void updateGameSpeed(int level) {
        brickPanel.setVisible(false);
        if( timeLine != null ){
            timeLine.stop();
            timeLine.getKeyFrames().clear();
        }
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(Level.LEVEL_SPEEDS[level]),
                e -> {
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
                    brickPanel.setVisible(true);
                }
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }
}