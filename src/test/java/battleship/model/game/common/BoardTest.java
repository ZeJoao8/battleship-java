package battleship.model.game.common;

import battleship.model.Position;
import battleship.model.Settings;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board newBoard(int w, int h) {
        return new Board(new Settings(w, h, Settings.BotDifficulty.EASY));
    }

    @Test
    void boardStartsWithAllWaterAndUnplacedFleet() {
        Board board = newBoard(5, 5);
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertEquals(CellState.WATER, board.getState(new Position(x, y)));
            }
        }
        board.getFleet().forEach(b -> assertFalse(b.isPlaced()));
    }

    @Property
    void isInsideIsTrueOnlyForValidCoordinates(
            @ForAll @IntRange(min = -5, max = 15) int x,
            @ForAll @IntRange(min = -5, max = 15) int y
    ) {
        Board board = newBoard(10, 10);
        Position p = new Position(x, y);
        boolean expected = x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
        assertEquals(expected, board.isInside(p));
    }

    @Test
    void markMissChangesCellToMiss() {
        Board board = newBoard(5, 5);
        Position p = new Position(2, 2);
        board.markMiss(p);
        assertEquals(CellState.MISS, board.getState(p));
    }

    @Test
    void markHitChangesCellToHit() {
        Board board = newBoard(5, 5);
        Position p = new Position(1, 1);
        board.markShip(p);
        board.markHit(p);
        assertEquals(CellState.HIT, board.getState(p));
    }

    @Test
    void validBotTargetOnlyForWaterOrShip() {
        Board board = newBoard(4, 4);
        Position p = new Position(1, 1);
        assertTrue(board.isValidBotTarget(p));
        board.markMiss(p);
        assertFalse(board.isValidBotTarget(p));
        board.markWater(p);
        board.markShip(p);
        assertTrue(board.isValidBotTarget(p));
        board.markHit(p);
        assertFalse(board.isValidBotTarget(p));
    }

    @Test
    void hittingBoatRegistersHitInBoat() {
        Board board = newBoard(5, 5);
        Boat boat = board.getFleet().get(0);
        Position p = new Position(0, 0);
        board.placeBoat(boat, p, true);
        board.markHit(p);
        assertTrue(boat.isPartHit(0));
        assertFalse(boat.isSunk());
    }

    @Test
    void hasLostOnlyWhenAllBoatsAreSunk() {
        Board board = newBoard(10, 10);
        for (Boat b : board.getFleet()) {
            board.placeBoat(b, new Position(0, board.getFleet().indexOf(b)), true);
            for (Position p : b.getPositions()) {
                board.markHit(p);
            }
        }
        assertTrue(board.hasLost());
    }

    @Test
    void allBoatsPlacedOnlyAfterAllPlaced() {
        Board board = newBoard(10, 10);
        assertFalse(board.allBoatsPlaced());
        for (Boat b : board.getFleet()) {
            board.placeBoat(b, new Position(0, board.getFleet().indexOf(b)), true);
        }
        assertTrue(board.allBoatsPlaced());
    }

    @Property
    void afterHitOrMissCellIsNeverWater(
            @ForAll @IntRange(min = 0, max = 4) int x,
            @ForAll @IntRange(min = 0, max = 4) int y
    ) {
        Board board = newBoard(5, 5);
        Position p = new Position(x, y);
        board.markMiss(p);
        assertNotEquals(CellState.WATER, board.getState(p));
        board.markWater(p);
        board.markShip(p);
        board.markHit(p);
        assertNotEquals(CellState.WATER, board.getState(p));
    }

    @Property
    void validBotTargetMatchesCellState(
            @ForAll CellState state
    ) {
        Board board = newBoard(3, 3);
        Position p = new Position(1, 1);
        switch (state) {
            case WATER -> board.markWater(p);
            case SHIP -> board.markShip(p);
            case HIT -> {
                board.markShip(p);
                board.markHit(p);
            }
            case MISS -> board.markMiss(p);
        }
        boolean expected = state == CellState.WATER || state == CellState.SHIP;
        assertEquals(expected, board.isValidBotTarget(p));
    }
}
