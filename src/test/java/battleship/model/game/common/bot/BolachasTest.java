package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BolachasTest {

    @Test
    void choosesFirstShipCellAvailable() {
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.BOLACHAS));
        b.placeBoat(b.getFleet().get(0), new Position(0,0), true);
        Bolachas bot = new Bolachas();
        Position p = bot.chooseMove(b);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    void choosesFirstValidWaterIfNoShips() {
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.BOLACHAS));
        Bolachas bot = new Bolachas();
        Position p = bot.chooseMove(b);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    void returns00IfNoValidTargetsExist() {
        Board b = new Board(new Settings(5,5, Settings.BotDifficulty.BOLACHAS));
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                b.markHit(new Position(x,y));
        Bolachas bot = new Bolachas();
        Position p = bot.chooseMove(b);
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }
}
