package battleship.model.game;

import net.jqwik.api.Property;
import net.jqwik.api.ForAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStatsTest {

    @Test
    void accuracyIsZeroWhenNoShots() {
        GameStats s = new GameStats();
        assertEquals(0,s.getPlayerAccuracy());
        assertEquals(0,s.getBotAccuracy());
    }

    @Test
    void accuracyComputedCorrectly() {
        GameStats s = new GameStats();
        s.registerPlayerShot(true);
        s.registerPlayerShot(false);
        assertEquals(50.0,s.getPlayerAccuracy());
    }

    @Property(tries = 20)
    void accuracyAlwaysBetweenZeroAndHundred(@ForAll int n) {
        GameStats s = new GameStats();
        int shots = Math.abs(n % 50);
        for(int i=0;i<shots;i++)
            s.registerPlayerShot(i%2==0);
        assertTrue(s.getPlayerAccuracy() >= 0);
        assertTrue(s.getPlayerAccuracy() <= 100);
    }
}
