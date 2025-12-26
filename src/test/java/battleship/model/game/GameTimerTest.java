package battleship.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    @Test
    void timerStartsAtZero() {
        GameTimer t = new GameTimer();
        assertEquals(0, t.getSeconds());
    }

    @Test
    void timerDoesNotIncreaseWhenStopped() {
        GameTimer t = new GameTimer();
        t.stop();
        int before = t.getSeconds();
        assertEquals(before, t.getSeconds());
    }
}
