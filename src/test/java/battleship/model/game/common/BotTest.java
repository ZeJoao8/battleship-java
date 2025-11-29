package battleship.model.game.common;

import battleship.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    static class TestBot extends Bot {
        public TestBot() { super("Teste", "Hard"); }
        @Override public Position chooseMove(Board b) { return new Position(1,1); }
    }

    @Test
    void constructorStoresNameAndDifficulty() {
        Bot bot = new TestBot();
        assertEquals("Teste", bot.getName());
        assertEquals("Hard", bot.getDifficulty());
    }

    @Test
    void chooseMoveIsImplementedBySubclasses() {
        Bot bot = new TestBot();
        assertEquals(new Position(1,1), bot.chooseMove(null));
    }
}
