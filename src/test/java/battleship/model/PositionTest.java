package battleship.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void moveUpWithinBounds() {
        Position p = new Position(2, 2);
        Position r = p.moveUpBounded();
        assertEquals(new Position(2, 1), r);
    }

    @Test
    void moveUpAtTopEdgeStaysAtZero() {
        Position p = new Position(3, 0);
        Position r = p.moveUpBounded();
        assertEquals(new Position(3, 0), r);
    }

    @Test
    void moveLeftWithinBounds() {
        Position p = new Position(2, 2);
        Position r = p.moveLeftBounded();
        assertEquals(new Position(1, 2), r);
    }

    @Test
    void moveLeftAtLeftEdgeStaysAtZero() {
        Position p = new Position(0, 5);
        Position r = p.moveLeftBounded();
        assertEquals(new Position(0, 5), r);
    }

    @Test
    void moveDownWithinBounds() {
        Position p = new Position(2, 2);
        Position r = p.moveDownBounded(9);
        assertEquals(new Position(2, 3), r);
    }

    @Test
    void moveDownAtBottomEdgeStopsAtMax() {
        Position p = new Position(4, 9);
        Position r = p.moveDownBounded(9);
        assertEquals(new Position(4, 9), r);
    }

    @Test
    void moveRightWithinBounds() {
        Position p = new Position(2, 2);
        Position r = p.moveRightBounded(9);
        assertEquals(new Position(3, 2), r);
    }

    @Test
    void moveRightAtRightEdgeStopsAtMax() {
        Position p = new Position(9, 4);
        Position r = p.moveRightBounded(9);
        assertEquals(new Position(9, 4), r);
    }

    @Test
    void equalsSameObject() {
        Position p = new Position(1, 1);
        assertEquals(p, p);
    }

    @Test
    void equalsSameCoordinates() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertEquals(p1, p2);
    }

    @Test
    void notEqualDifferentCoordinates() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(2, 1);
        assertNotEquals(p1, p2);
    }

    @Test
    void notEqualDifferentClassOrNull() {
        Position p = new Position(1, 1);
        assertNotEquals(p, null);
        assertNotEquals(p, "not a position");
    }
}
