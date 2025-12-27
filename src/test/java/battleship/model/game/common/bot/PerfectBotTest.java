package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.CellState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfectBotTest {

    private Board board() {
        return new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
    }

    @Test
    void choosesShipCellWhenShipExists() {
        Board board = board();
        board.placeFleetRandomly();
        Perfect bot = new Perfect();
        Position p = bot.chooseMove(board);
        assertNotNull(p);
        assertEquals(CellState.SHIP, board.getState(p));
    }

    @Test
    void fallsBackToAnyValidTargetWhenNoShips() {
        Board board = board();
        Perfect bot = new Perfect();
        Position p = bot.chooseMove(board);
        assertNotNull(p);
        assertTrue(board.isValidBotTarget(p));
    }

    @Test
    void handlesBoardWithAllInvalidTargetsGracefully() {
        Board board = board();
        board.placeFleetRandomly();
        for (int y = 0; y < board.getHeight(); y++)
            for (int x = 0; x < board.getWidth(); x++)
                board.markMiss(new Position(x,y));
        Perfect bot = new Perfect();
        Position p = bot.chooseMove(board);
        assertNotNull(p);
    }

    @Test
    void resumesPendingTargetAfterFinishingCurrentOne() {
        Board board = new Board(new Settings(10,10, Settings.BotDifficulty.IMPOSSIBLE));
        Boat a = board.getFleet().get(0);
        Boat b = board.getFleet().get(1);
        board.placeBoat(a, new Position(0,0), true);
        board.placeBoat(b, new Position(0,3), true);
        Probability bot = new Probability();
        Position a1;
        a1 = a.getPositions().get(0);
        board.markHit(a1);
        bot.registerShot(board, a1);
        Position b1 = b.getPositions().get(0);
        board.markHit(b1);
        bot.registerShot(board, b1);
        for (Position p : a.getPositions()) {
            if (!p.equals(a1)) {
                board.markHit(p);
                bot.registerShot(board, p);
            }
        }
        Position next = bot.chooseMove(board);
        assertTrue(board.isInside(next));
        assertTrue(board.isValidBotTarget(next));
    }
}
