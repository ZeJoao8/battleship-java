package battleship.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void gettersReturnCorrectValues() {
        Position p = new Position(7, 3);
        assertEquals(7, p.getX());
        assertEquals(3, p.getY());
    }

    @Test
    void moveUpBounded_movesNormallyWhenAboveZero() {
        Position p = new Position(4, 5);
        assertEquals(new Position(4, 4), p.moveUpBounded());
    }

    @Test
    void moveUpBounded_staysAtZero() {
        Position p = new Position(4, 0);
        assertEquals(new Position(4, 0), p.moveUpBounded());
    }

    @Test
    void moveLeftBounded_movesNormally() {
        Position p = new Position(5, 6);
        assertEquals(new Position(4, 6), p.moveLeftBounded());
    }

    @Test
    void moveLeftBounded_staysAtZero() {
        Position p = new Position(0, 6);
        assertEquals(new Position(0, 6), p.moveLeftBounded());
    }

    @Test
    void moveDownBounded_movesNormallyIfNotAtLimit() {
        Position p = new Position(3, 2);
        assertEquals(new Position(3, 3), p.moveDownBounded(5));
    }

    @Test
    void moveDownBounded_clampsToMax() {
        Position p = new Position(3, 7);
        assertEquals(new Position(3, 7), p.moveDownBounded(7));
    }

    @Test
    void moveRightBounded_movesNormallyIfNotAtLimit() {
        Position p = new Position(2, 4);
        assertEquals(new Position(3, 4), p.moveRightBounded(5));
    }

    @Test
    void moveRightBounded_clampsToMax() {
        Position p = new Position(5, 4);
        assertEquals(new Position(5, 4), p.moveRightBounded(5));
    }

    @Test
    void equalsSelf() {
        Position p = new Position(1, 1);
        assertEquals(p, p);
    }

    @Test
    void equalsNullFalse() {
        Position p = new Position(1, 1);
        assertNotEquals(null, p);
    }

    @Test
    void equalsDifferentClassFalse() {
        Position p = new Position(1, 1);
        assertNotEquals(p, "not a position");
    }

    @Test
    void equalsDifferentXFalse() {
        assertNotEquals(new Position(2, 3), new Position(9, 3));
    }

    @Test
    void equalsDifferentYFalse() {
        assertNotEquals(new Position(2, 3), new Position(2, 9));
    }

    @Test
    void equalsSameCoordinatesTrue() {
        assertEquals(new Position(6, 2), new Position(6, 2));
    }
}
