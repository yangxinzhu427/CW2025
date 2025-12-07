# GitHub
**Github repository** https://github.com/yangxinzhu427/CW2025<br>
# Compilation Instructions<br>
## Prerequisites
1. Install openjfx and openjdk.<br>
2. Install Maven.
## Intellij Idea
1. Click file on the left top of Intellij.<br>
2. Then click Project Structure.<br>
3. In Project, select JDK version 25 in SDK and SDK default in Language level.<br>
4. Then click Modules, add all the jar files in lib folder in openjfk to dependecies.<br>
5. In the main menu click Run / Debug Configurations.<br>
6. Click Edit Configurations.<br>
7. In Build and run, choose java OPENJDK 25.<br>
8. Copy and paste 'com.comp2042.application.Main' in Main class.<br>
9. Click Modify options, Add VM options.<br>
10. Copy and paste '--module-path "PATH-TO-FX-LIB" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics' 
in VM options.<br>
## Command Line
1. Open project’s root directory.<br>
2. Run Maven commands in the terminal.
```bash
mvn clean compile
```
```bash
mvn exec:java -Dexec.mainClass="com.comp2042.application.Main"
```
# Implemented and Working Properly
1. **feat: pause game.**<br>
Player can pause or unpause the game by pressing the 'P' key. 
When paused, a "PAUSED" text shows on the screen, and the game timer stops.<br>
2. **feat: display score.**<br>
Display current score on game board.<br>
3. **feat: display next brick shape.**
A preview panel displays the shape of the next brick that is ready to fall.<br>
4. **feat: display high score**<br>
When game over, high score on game over panel will be displayed.<br> 
5. **feat: main menu scene.**<br>
The initial scene when open the game, providing start, tutorial and exit button.<br>
6. **feat: hard drop.**<br>
Player can drop the brick immediately by pressing the 'SPACE' key.<br>
7. **feat: congratulations panel.**<br>
When player completed all levels, congratulations panel will be displayed.<br>
8. **feat: levels in the game.**<br>
The game advances a level when the score achieved the threshold, and the brick fall speed accelerates.<br>
9. **feat: area clear brick.**<br>
A special brick that can clear 5x5 area around its landing spot when it removed as part of a line clear.<br> 
10. **feat: instruction panel.**<br>
A panel displays tutorial information.<br>
11. **refractor: packages name.**<br>
Packages have been restructured to improve readability and maintainability.<br>

# Features Not Implemented
1. **Mutiple game modes**<br>
I didn't develop multiple modes due to time constraints and focus on the standard game loop and prop brick system.<br>
2. **Ghost bricks**<br>

# New Java Classes
1. **com.comp2042.model.game.data.Level.class**<br>
Level class defines and configure level difficulty, threshold score, and entity classes that store the current number of levels.<br>
2. **com.comp2042.view.panel.CongratulationsPanel.class**<br>
CongratulationsPanel class defines display method of "You Win!" text when the player wins.<br>
3. **com.comp2042.model.bricks.shape.PropBrick.class**<br>
PropBrick class defines and configures the prop block interface.<br>
4. **com.comp2042.model.bricks.shape.AreaClearBrick.class**<br>
AreaClearBrick class is a prop brick class that executes an area clearing effect when removed from the board.<br>
5. **MatrixOperationsTest.class**<br> 
Test class for testing methods in MatrixOperations and AreaClearBrick class.<br>
6. **ScoreTest.class**
Test class for testing methods in Score class.<br>
7. **com.comp2042.model.game.data.Prop.class** <br>
Prop class defines and configures prop brick's position and type information.<br>
8. **com.comp2042.controller.MainMenuController.class**<br>
MainMenuController class handles interactions in menu.<br>
9. **com.comp2042.controller.InstructionsController.class**<br>
InstructionsController class defines display method of tutorial.<br>

# Modified Java Classes
1. **Main**<br>
Changed from loading gameLayout.fxml to loading mainMenuLayout.fxml. Shows main menu when the application launched.<br>
Removed direct initialization of GameController and GuiController.<>br
Added dependency on and used MainMenuController to pass the primaryStage reference.<br>
Added primaryStage.setResizable(false); to prevent the player resizing the main window.<br>
2. **GuiController**<br>
Implemented Pause functionality, Hard Drop logic, and Game Over state handling.<br>
Added methods bindScore() and bindLevel().<br>
Integrated logic for displaying high score and the next brick preview.<br>
Added updateGameSpeed() to control the timeLine based on the current level index, implemented completeLevels().<br>
Modified getFillColor() to include case 8. Enabled to display area clear brick correctly.<br>
Removed the decorative Reflection effect initialization.<br>
3. **GameController**<br>
Added private method checkAndAdvanceLevel() to compare the score against level thresholds, handle level transitions, update game speed, and trigger the completeLevels() win condition.<br>
Added onHardDropEvent(MoveEvent event) implementation. 
This method performs a hard drop, merges, clears rows, checks for game over, and calls checkAndAdvanceLevel().<br>
Added a call to viewGuiController.bindLevel() and removed all level resets from createNewGame().<br>
Added checkAndAdvanceLevel() calls at the end of both onDownEvent() and onHardDropEvent().<br>
4. **SimpleBoard**<br>
Added getLevel() to get level information.<br> 
Added nextGame() to reset game board in next level.<br>

5. **RandomBrickGenerator**<br>
Added propBrickMap and static {} to register and store AreaClearBrick instances.<br>
Added static method getPropBrick(int value) to allow other classes to get PropBrick instance by its color code.<br>
Modified getBrick() to check random condition (ThreadLocalRandom.current().nextInt(brickList.size() * 10) == brickList.size()) to randomly add a Prop Brick instance to the nextBricks queue.<br>
6. **BrickRotator**<br>
Added imports for NextShapeInfo and PropBrick.<br>
Added the isPropBrick() method to check whether the falling brick is a prop brick or not.<br>

7. **MatrixOperations**<br>
Added imports for PropBrick, RandomBrickGenerator, Prop, and Point.<br>
Rewrote checkRemoving() method for detecting, triggering the area clearing effect, and scoring the specialized Prop Bricks.<br>
Changed how rows are added to newRows in checkRemoving().<br>
8. **ViewData**<br>
Added isProp() to check whether the falling brick is prop brick or not.<br>
# Unexpected Problems
1. Fixed a bug where the block was moved to the right by a small piece at the start of the game.
2. Fixed the bug that the initial position of the block was in the middle of the entire board.
