package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HuntBotTest {

    static class FixedRandom extends Random {
        private final int[] values;
        private int i = 0;

        FixedRandom(int... values) {
            this.values = values;
        }

        @Override
        public int nextInt(int bound) {
            return values[i++ % values.length];
        }
    }

    private Board board() {
        return new Board(new Settings(10, 10, Settings.BotDifficulty.MEDIUM));
    }

    @Test
    void randomHuntAcceptsEvenParity() {
        Board board = board();
        Hunt bot = new Hunt(new FixedRandom(1,0, 2,0));
        Position p = bot.chooseMove(board);
        assertEquals(2, p.getX());
        assertEquals(0, p.getY());
        assertTrue(board.isValidBotTarget(p));
    }

    @Test
    void randomHuntSkipsInvalidTarget() {
        Board board = board();
        board.markMiss(new Position(2,0));
        Hunt bot = new Hunt(new FixedRandom(2,0, 4,0));
        Position p = bot.chooseMove(board);
        assertEquals(new Position(4,0), p);
    }

    @Test
    void hitAddsNeighborsToQueue() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(2,2), true);
        Hunt bot = new Hunt(new FixedRandom(2,2));
        Position p1 = bot.chooseMove(board);
        assertEquals(new Position(2,2), p1);
        Position p2 = bot.chooseMove(board);
        assertTrue(board.isInside(p2));
        assertTrue(board.isValidBotTarget(p2));
    }

    @Test
    void queuedPositionIsUsedBeforeRandom() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(2,2), true);
        Hunt bot = new Hunt(new FixedRandom(5,5));
        Position hit = new Position(2,2);
        board.markHit(hit);
        bot.chooseMove(board);
        Position next = bot.chooseMove(board);
        assertTrue(board.isInside(next));
        assertTrue(board.isValidBotTarget(next));
    }

    @Test
    void invalidQueuedPositionsAreSkipped() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(0,0), true);
        Hunt bot = new Hunt(new FixedRandom(4,4));
        Position hit = new Position(0,0);
        board.markHit(hit);
        bot.chooseMove(board);
        board.markMiss(new Position(1,0));
        board.markMiss(new Position(0,1));
        Position p = bot.chooseMove(board);
        assertEquals(new Position(4,4), p);
    }

    @Test
    void addRejectsOutsideBoard() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(0,0), true);
        Hunt bot = new Hunt(new FixedRandom(3,3));
        board.markHit(new Position(0,0));
        bot.chooseMove(board);
        Position p = bot.chooseMove(board);
        assertEquals(new Position(3,3), p);
    }

    @Test
    void missDoesNotPopulateQueue() {
        Board board = board();
        Hunt bot = new Hunt(new FixedRandom(2,2));
        Position miss = new Position(1,1);
        board.markMiss(miss);
        Position p = bot.chooseMove(board);
        assertEquals(new Position(2,2), p);
    }
}
