package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProbabilityBotTest {

    static class TestableProbability extends Probability {
        boolean sameX() { return allSameX(); }
        boolean sameY() { return allSameY(); }
    }

    private Board board() {
        return new Board(new Settings(10, 10, Settings.BotDifficulty.HARD));
    }

    @Test
    void huntModeReturnsValidTarget() {
        Board board = board();
        TestableProbability bot = new TestableProbability();
        Position p = bot.chooseMove(board);
        assertTrue(board.isInside(p));
        assertTrue(board.isValidBotTarget(p));
    }

    @Test
    void singleHitCreatesTargets() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(4,4), true);
        TestableProbability bot = new TestableProbability();
        Position hit = new Position(4,4);
        board.markHit(hit);
        bot.registerShot(board, hit);
        Position next = bot.chooseMove(board);
        assertTrue(board.isValidBotTarget(next));
    }

    @Test
    void detectsHorizontalAlignment() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(2,5), true);
        TestableProbability bot = new TestableProbability();
        Position h1 = new Position(2,5);
        Position h2 = new Position(3,5);
        board.markHit(h1);
        board.markHit(h2);
        bot.registerShot(board, h1);
        bot.registerShot(board, h2);
        assertTrue(bot.sameY());
        assertFalse(bot.sameX());
    }

    @Test
    void detectsVerticalAlignment() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(6,2), false);
        TestableProbability bot = new TestableProbability();
        Position h1 = new Position(6,2);
        Position h2 = new Position(6,3);
        board.markHit(h1);
        board.markHit(h2);
        bot.registerShot(board, h1);
        bot.registerShot(board, h2);
        assertTrue(bot.sameX());
        assertFalse(bot.sameY());
    }

    @Test
    void mixedHitsTriggerNoAlignmentBranch() throws Exception {
        TestableProbability bot = new TestableProbability();
        Field clusterField = null;
        for (Field f : Probability.class.getDeclaredFields()) {
            if (List.class.isAssignableFrom(f.getType())) {
                clusterField = f;
                break;
            }
        }
        assertNotNull(clusterField);
        clusterField.setAccessible(true);
        List<Position> cluster = (List<Position>) clusterField.get(bot);
        cluster.clear();
        cluster.add(new Position(1, 1));
        cluster.add(new Position(2, 2));
        assertFalse(bot.sameX());
        assertFalse(bot.sameY());
        Board board = board();
        Position next = bot.chooseMove(board);
        assertTrue(board.isValidBotTarget(next));
    }

    @Test
    void resetsAfterSunkCluster() {
        Board board = board();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(0,0), true);
        TestableProbability bot = new TestableProbability();
        for (Position p : boat.getPositions()) {
            board.markHit(p);
            bot.registerShot(board, p);
        }
        Position next = bot.chooseMove(board);
        assertTrue(board.isValidBotTarget(next));
    }
}
