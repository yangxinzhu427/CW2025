import static org.junit.jupiter.api.Assertions.*;
import com.comp2042.model.game.data.Score;
import org.junit.jupiter.api.Test;

class ScoreTest {


    @Test
    void testInitialScoreIsZero() {
        Score score = new Score();
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    void testAddIncreasesScore() {
        Score score = new Score();
        score.add(50);
        assertEquals(50, score.scoreProperty().get());
    }

    @Test
    void testResetSetsToZero() {
        Score score = new Score();
        score.add(100);
        score.reset();
        assertEquals(0, score.scoreProperty().get());
    }
}