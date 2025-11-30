package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfectBotTest {

    @Test
    void choosesFirstShipCellAvailable() {
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.PERFECT));
        b.placeBoat(b.getFleet().get(0), new Position(0,0), true);
        PerfectBot bot = new PerfectBot();
        Position p = bot.chooseMove(b);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    void choosesFirstValidWaterIfNoShips() {
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.PERFECT));
        PerfectBot bot = new PerfectBot();
        Position p = bot.chooseMove(b);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    void returns00IfNoValidTargetsExist() {
        Board b = new Board(new Settings(5,5, Settings.BotDifficulty.PERFECT));
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                b.markHit(new Position(x,y));
        PerfectBot bot = new PerfectBot();
        Position p = bot.chooseMove(b);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }
}
