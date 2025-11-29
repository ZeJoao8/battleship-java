package battleship.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void gettersAndEquality() {
        Position p = new Position(7,3);
        assertEquals(7,p.getX());
        assertEquals(3,p.getY());
        assertEquals(new Position(7,3), p);
        assertNotEquals(new Position(7,4), p);
        assertNotEquals(new Position(8,3), p);
        assertNotEquals(null, p);
        assertNotEquals("not-pos", p);
    }

    @Test
    void moveUpAndLeftRespectZeroClamp() {
        assertEquals(new Position(4,0), new Position(4,1).moveUpBounded());
        assertEquals(new Position(4,0), new Position(4,0).moveUpBounded());
        assertEquals(new Position(0,5), new Position(1,5).moveLeftBounded());
        assertEquals(new Position(0,5), new Position(0,5).moveLeftBounded());
    }

    @Test
    void moveDownClampsAtMax() {
        assertEquals(new Position(3,3), new Position(3,2).moveDownBounded(7));
        assertEquals(new Position(3,7), new Position(3,7).moveDownBounded(7));
        assertEquals(new Position(3,7), new Position(3,8).moveDownBounded(7));
    }

    @Test
    void moveRightClampsAtMax() {
        assertEquals(new Position(5,4), new Position(4,4).moveRightBounded(6));
        assertEquals(new Position(6,4), new Position(6,4).moveRightBounded(6));
        assertEquals(new Position(6,4), new Position(9,4).moveRightBounded(6));
    }
}

