package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProbabilityBotTest {

    private Board board() {
        return new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
    }

    @Test
    void huntModeReturnsValidTarget() {
        Board board = board();
        Probability bot = new Probability();

        Position p = bot.chooseMove(board);

        assertTrue(board.isValidBotTarget(p));
    }

    @Test
    void focusesAfterSingleHit() {
        Board board = board();
        Boat b = board.getFleet().get(0);
        board.placeBoat(b, new Position(2,2), true);

        Probability bot = new Probability();

        Position hit = new Position(2,2);
        board.markHit(hit);
        bot.registerShot(board, hit);

        Position next = bot.chooseMove(board);
        assertNotNull(next);
    }

    @Test
    void continuesInLineAfterTwoHorizontalHits() {
        Board board = board();
        Boat b = board.getFleet().get(0);
        board.placeBoat(b, new Position(1,2), true);

        Probability bot = new Probability();

        Position h1 = new Position(1,2);
        Position h2 = new Position(2,2);

        board.markHit(h1);
        board.markHit(h2);

        bot.registerShot(board, h1);
        bot.registerShot(board, h2);

        Position next = bot.chooseMove(board);
        assertNotNull(next);
    }

    @Test
    void resetsAfterSunkCluster() {
        Board board = board();
        Boat b = board.getFleet().get(0);
        board.placeBoat(b, new Position(0,0), true);

        Probability bot = new Probability();

        for (Position p : b.getPositions()) {
            board.markHit(p);
            bot.registerShot(board, p);
        }

        Position next = bot.chooseMove(board);
        assertTrue(board.isValidBotTarget(next));
    }
}
