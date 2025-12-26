package battleship.model.game.common;

import battleship.model.Position;
import battleship.model.Settings;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardPlacementServiceTest {
    private Board newBoard(int w, int h) {
        return new Board(new Settings(w, h, Settings.BotDifficulty.EASY));
    }
    private Boat newBoat(int size) {
        return new Boat("TestBoat", size) {};
    }

    @Test
    void canPlaceBoatOnEmptyBoardHorizontally() {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(3);
        assertTrue(new BoardPlacementService().canPlace(board, boat, new Position(1, 2), true));
    }

    @Test
    void cannotPlaceBoatOutsideBoardHorizontally() {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(3);
        assertFalse(new BoardPlacementService().canPlace(board, boat, new Position(4, 2), true));
    }

    @Test
    void cannotPlaceBoatOutsideBoardVertically() {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(3);
        assertFalse(new BoardPlacementService().canPlace(board, boat, new Position(2, 4), false));
    }

    @Test
    void cannotPlaceBoatOverAnotherBoat() {
        Board board = newBoard(5, 5);
        Boat boat1 = newBoat(2);
        Boat boat2 = newBoat(3);
        BoardPlacementService s = new BoardPlacementService();
        s.place(board, boat1, new Position(1, 1), true);
        assertFalse(s.canPlace(board, boat2, new Position(0, 1), true));
    }

    @Test
    void placeBoatMarksBoardAndBoatPositions() {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(3);
        BoardPlacementService s = new BoardPlacementService();
        s.place(board, boat, new Position(1, 1), true);
        assertTrue(boat.isPlaced());
        assertEquals(3, boat.getPositions().size());
        for (Position p : boat.getPositions()) {
            assertEquals(CellState.SHIP, board.getState(p));
        }
    }

    @Test
    void placeDoesNothingIfPlacementIsInvalid() {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(3);
        BoardPlacementService s = new BoardPlacementService();
        s.place(board, boat, new Position(4, 4), true);
        assertFalse(boat.isPlaced());
        assertTrue(boat.getPositions().isEmpty());
    }

    @Property
    void validPlacementAlwaysPlacesBoatFullyInsideBoard(
            @ForAll @IntRange(min = 1, max = 4) int size,
            @ForAll @IntRange(min = 0, max = 4) int x,
            @ForAll @IntRange(min = 0, max = 4) int y,
            @ForAll boolean horizontal
    ) {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(size);
        BoardPlacementService s = new BoardPlacementService();
        Position start = new Position(x, y);
        if (s.canPlace(board, boat, start, horizontal)) {
            s.place(board, boat, start, horizontal);
            for (Position p : boat.getPositions()) {
                assertTrue(board.isInside(p));
                assertEquals(CellState.SHIP, board.getState(p));
            }
        }
    }

    @Property
    void canPlaceMatchesBoardState(
            @ForAll @IntRange(min = 1, max = 4) int size,
            @ForAll boolean horizontal
    ) {
        Board board = newBoard(5, 5);
        Boat boat = newBoat(size);
        BoardPlacementService s = new BoardPlacementService();
        Position start = new Position(0, 0);
        boolean canPlace = s.canPlace(board, boat, start, horizontal);
        s.place(board, boat, start, horizontal);
        if (canPlace) {
            assertTrue(boat.isPlaced());
        } else {
            assertFalse(boat.isPlaced());
        }
    }
}
