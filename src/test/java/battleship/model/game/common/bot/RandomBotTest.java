package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBotTest {

    private Board board() {
        return new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
    }

    @Test
    void alwaysReturnsValidTarget() {
        Board b = board();
        Random bot = new Random();
        for (int i = 0; i < 100; i++) {
            Position p = bot.chooseMove(b);
            assertTrue(b.isInside(p));
            assertTrue(b.isValidBotTarget(p));
        }
    }

    @Test
    void doesNotRepeatAlreadyShotPositions() {
        Board b = board();
        Random bot = new Random();
        Position first = bot.chooseMove(b);
        b.markMiss(first);
        Position second = bot.chooseMove(b);
        assertNotEquals(first, second);
    }
}
