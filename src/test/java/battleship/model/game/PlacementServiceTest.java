package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementServiceTest {

    private Board newBoard() {
        return new Board(new Settings(10, 10, Settings.BotDifficulty.EASY));
    }

    @Test
    void autoPlacePlacesAllBoats() {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        service.autoPlace(board);
        board.getFleet().forEach(b -> assertTrue(b.isPlaced()));
    }

    @Test
    void autoPlaceClearsPreviousPlacements() {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        Boat boat = board.getFleet().get(0);
        board.placeBoat(boat, new Position(0, 0), true);
        service.autoPlace(board);
        board.getFleet().forEach(b -> assertTrue(b.isPlaced()));
    }

    @Test
    void undoAfterAutoPlaceClearsAllBoats() {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        service.autoPlace(board);
        service.undo(board, true);
        board.getFleet().forEach(b -> {
            assertFalse(b.isPlaced());
            assertTrue(b.getPositions().isEmpty());
        });
    }

    @Test
    void undoRemovesLastPlacedBoatOnly() {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        Boat first = board.getFleet().get(0);
        Boat second = board.getFleet().get(1);
        board.placeBoat(first, new Position(0, 0), true);
        board.placeBoat(second, new Position(0, 1), true);
        service.undo(board, false);
        assertTrue(first.isPlaced());
        assertFalse(second.isPlaced());
    }

    @Test
    void undoDoesNothingIfNoBoatPlaced() {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        service.undo(board, false);
        board.getFleet().forEach(b -> assertFalse(b.isPlaced()));
    }

    @Property
    void undoNeverLeavesBoardInInvalidState(@ForAll boolean autoPlaced) {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        service.autoPlace(board);
        service.undo(board, autoPlaced);
        for (Boat b : board.getFleet()) {
            if (!b.isPlaced()) {
                assertTrue(b.getPositions().isEmpty());
            } else {
                for (Position p : b.getPositions()) {
                    assertTrue(board.isInside(p));
                }
            }
        }
    }

    @Property
    void afterAutoPlaceBoardHasNoWaterUnderBoats() {
        Board board = newBoard();
        PlacementService service = new PlacementService();
        service.autoPlace(board);
        for (Boat b : board.getFleet()) {
            for (Position p : b.getPositions()) {
                assertTrue(board.isShip(p));
            }
        }
    }
}
