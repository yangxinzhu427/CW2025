import com.comp2042.model.game.data.ClearRow;
import com.comp2042.model.game.data.MatrixOperations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void shouldClearOneFullRowAndReturnCorrectBonus() {
        int[][] matrix = {
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1}, // full row
                {0, 1, 0, 1, 0}
        };

        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(1, result.getLinesRemoved());
        assertEquals(50, result.getScoreBonus());
    }

    @Test
    void shouldClearTwoFullRowsAndReturnHigherBonus() {
        int[][] matrix = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 0, 1, 0}
        };

        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(2, result.getLinesRemoved());
        assertEquals(200, result.getScoreBonus());
    }

    @Test
    void shouldDetectPowerBrickInClearedRow() {
        int[][] matrix = {
                {0, 0, 0, 0, 0},
                {8, 8, 8, 8, 8},
                {0, 1, 0, 1, 0}
        };

        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(1, result.getLinesRemoved());
    }

    @Test
    void shouldNotClearIncompleteRow() {
        int[][] matrix = {
                {1, 1, 0, 1, 1}, // not full
                {0, 0, 0, 0, 0}
        };

        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(0, result.getLinesRemoved());
        assertEquals(0, result.getScoreBonus());
    }

    @Test
    void shouldClearPowerBrickInClearedRow(){
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0},
                {1, 1, 8, 1, 1,1,1},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 0,0, 0},
                {0, 1, 0, 1, 0,0, 0},
                {0, 1, 0, 1, 0,0, 0},
                {0, 1, 0, 1, 0,0, 0},
        };
        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(1, result.getLinesRemoved());
        assertEquals(150, result.getScoreBonus());
        assertEquals(0, result.getNewMatrix()[2][1]);
    }

    @Test
    void shouldClearMultiplePowerBrickInClearedRow(){
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0},
                {1, 1, 8, 1, 1,8,1},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 0,1, 0},
                {0, 1, 0, 1, 0,0, 0},
        };
        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(1, result.getLinesRemoved());
        assertEquals(250, result.getScoreBonus());
        assertEquals(0, result.getNewMatrix()[2][1]);
        assertEquals(0, result.getNewMatrix()[4][4]);
        assertEquals(1, result.getNewMatrix()[6][4]);
    }
    @Test
    void shouldClearPowerBrickInClearedRowClearPowerBrick(){
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0},
                {1, 1, 8, 1, 1,8,1},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 8, 1, 1,1, 0},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 0, 1, 1,1, 0},
                {0, 1, 1, 1, 1,1, 0},
                {0, 1, 0, 1, 0,0, 0},
        };
        ClearRow result = MatrixOperations.checkRemoving(matrix);
        assertEquals(1, result.getLinesRemoved());
        assertEquals(250, result.getScoreBonus());
        assertEquals(0, result.getNewMatrix()[2][1]);
        assertEquals(0, result.getNewMatrix()[4][4]);
        assertEquals(0, result.getNewMatrix()[5][2]);
        assertEquals(1, result.getNewMatrix()[7][2]);
        assertEquals(1, result.getNewMatrix()[7][4]);
    }

}