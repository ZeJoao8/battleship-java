package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.CellState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfectBotTest {

    @Test
    void choosesShipCellWhenOneExists() {
        Board board = new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
        board.placeFleetRandomly();

        Perfect bot = new Perfect();
        Position p = bot.chooseMove(board);

        assertNotNull(p);
        assertEquals(CellState.SHIP, board.getState(p));
    }

    @Test
    void alwaysReturnsAValidTarget() {
        Board board = new Board(new Settings(6,6, Settings.BotDifficulty.EASY));

        Perfect bot = new Perfect();
        Position p = bot.chooseMove(board);

        assertNotNull(p);
        assertTrue(board.isValidBotTarget(p));
    }
}
