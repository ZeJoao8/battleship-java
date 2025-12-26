package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.Settings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBotTest {

    @Test
    void alwaysReturnsValidTarget() {
        Board b = new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
        Random bot = new Random();
        for(int i=0;i<50;i++){
            Position p = bot.chooseMove(b);
            assertTrue(b.isInside(p));
            assertTrue(b.isValidBotTarget(p));
        }
    }

    @Test
    void neverRepeatsInvalidShots() {
        Board b = new Board(new Settings(5,5, Settings.BotDifficulty.EASY));
        Random bot = new Random();
        Position p = bot.chooseMove(b);
        b.markMiss(p);
        Position p2 = bot.chooseMove(b);
        assertNotEquals(p,p2);
    }
}
