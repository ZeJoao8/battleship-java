package battleship.model.game.common;

import battleship.model.Position;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {
    static class TestBoat extends Boat {
        TestBoat(int size) {
            super("TestBoat", size);
        }
    }

    @Test
    void boatStartsUnplacedAndNotSunk() {
        Boat boat = new TestBoat(3);
        assertFalse(boat.isPlaced());
        assertFalse(boat.isSunk());
        assertEquals(3, boat.getSize());
        assertTrue(boat.isHorizontal());
    }

    @Test
    void boatBecomesPlacedWhenPositionsAreAdded() {
        Boat boat = new TestBoat(2);
        boat.addPosition(new Position(0, 0));
        boat.addPosition(new Position(1, 0));
        assertTrue(boat.isPlaced());
        assertEquals(2, boat.getPositions().size());
    }

    @Test
    void clearPositionsResetsPlacementAndHits() {
        Boat boat = new TestBoat(2);
        boat.addPosition(new Position(0, 0));
        boat.addPosition(new Position(1, 0));
        boat.registerHit(new Position(0, 0));
        boat.clearPositions();
        assertFalse(boat.isPlaced());
        assertFalse(boat.isSunk());
        assertTrue(boat.getPositions().isEmpty());
    }

    @Test
    void registeringHitMarksCorrectPart() {
        Boat boat = new TestBoat(2);
        Position p1 = new Position(0, 0);
        Position p2 = new Position(1, 0);
        boat.addPosition(p1);
        boat.addPosition(p2);
        boat.registerHit(p2);
        assertFalse(boat.isPartHit(0));
        assertTrue(boat.isPartHit(1));
    }

    @Test
    void boatIsSunkOnlyWhenAllPartsHit() {
        Boat boat = new TestBoat(3);
        List<Position> positions = List.of(
                new Position(0, 0),
                new Position(1, 0),
                new Position(2, 0)
        );
        positions.forEach(boat::addPosition);
        boat.registerHit(positions.get(0));
        assertFalse(boat.isSunk());
        boat.registerHit(positions.get(1));
        assertFalse(boat.isSunk());
        boat.registerHit(positions.get(2));
        assertTrue(boat.isSunk());
    }

    @Test
    void rotateTogglesOrientation() {
        Boat boat = new TestBoat(2);
        boolean initial = boat.isHorizontal();
        boat.rotate();
        assertNotEquals(initial, boat.isHorizontal());
        boat.rotate();
        assertEquals(initial, boat.isHorizontal());
    }

    @Property
    void boatIsSunkIffAllPartsAreHit(@ForAll @IntRange(min = 1, max = 5) int size) {
        Boat boat = new TestBoat(size);
        for (int i = 0; i < size; i++) {
            boat.addPosition(new Position(i, 0));
        }
        for (int i = 0; i < size - 1; i++) {
            boat.registerHit(new Position(i, 0));
            assertFalse(boat.isSunk());
        }
        boat.registerHit(new Position(size - 1, 0));
        assertTrue(boat.isSunk());
    }

    @Property
    void isPartHitOnlyTrueForHitPositions(@ForAll @IntRange(min = 1, max = 5) int size) {
        Boat boat = new TestBoat(size);
        for (int i = 0; i < size; i++) {
            boat.addPosition(new Position(i, 0));
        }
        boat.registerHit(new Position(0, 0));
        for (int i = 0; i < size; i++) {
            if (i == 0)
                assertTrue(boat.isPartHit(i));
            else
                assertFalse(boat.isPartHit(i));
        }
    }
}
