package battleship.model.game.common;

import battleship.model.Position;
import battleship.model.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    static class TestBot extends Bot {
        TestBot() { super("Teste", "Hard"); }
        @Override public Position chooseMove(Board b) { return new Position(1,1); }
    }

    @Test
    void constructorStoresNameAndDifficulty() {
        Bot bot = new TestBot();
        assertEquals("Teste", bot.getName());
        assertEquals("Hard", bot.getDifficulty());
    }

    @Test
    void chooseMoveIsDelegatedToSubclass() {
        Bot bot = new TestBot();
        Board b = new Board(new Settings(5,5, Settings.BotDifficulty.EASY));
        assertEquals(new Position(1,1), bot.chooseMove(b));
    }
}
