package battleship.model.game.common;

import battleship.model.Position;
import battleship.model.game.common.boats.NavioTanque;
import battleship.model.game.common.boats.PortaAvioes;
import battleship.model.game.common.boats.Submarino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {

    private Boat smallBoat;
    private Boat bigBoat;

    @BeforeEach
    void setup() {
        smallBoat = new Submarino();
        bigBoat   = new PortaAvioes();
    }

    @Test
    void constructorValuesCorrect() {
        assertEquals(2, smallBoat.getSize());
        assertEquals(5, bigBoat.getSize());
        assertTrue(smallBoat.getPositions().isEmpty());
        assertTrue(bigBoat.getPositions().isEmpty());
        assertTrue(smallBoat.isHorizontal());
        assertFalse(smallBoat.isSunk());
        assertFalse(smallBoat.isPlaced());
    }

    @Test
    void setHorizontalAndRotate() {
        smallBoat.setHorizontal(false);
        assertFalse(smallBoat.isHorizontal());
        smallBoat.rotate();
        assertTrue(smallBoat.isHorizontal());
        smallBoat.rotate();
        assertFalse(smallBoat.isHorizontal());
    }

    @Test
    void addPositionAndClear() {
        bigBoat.addPosition(new Position(2,2));
        bigBoat.addPosition(new Position(3,2));
        assertEquals(2, bigBoat.getPositions().size());
        assertTrue(bigBoat.isPlaced());
        bigBoat.clearPositions();
        assertTrue(bigBoat.getPositions().isEmpty());
        assertFalse(bigBoat.isPlaced());
    }

    @Test
    void registerHitMarksCorrectIndex() {
        bigBoat.addPosition(new Position(1,1));
        bigBoat.addPosition(new Position(2,1));
        bigBoat.addPosition(new Position(3,1));
        bigBoat.registerHit(new Position(2,1));
        assertFalse(bigBoat.isPartHit(0));
        assertTrue(bigBoat.isPartHit(1));
        assertFalse(bigBoat.isPartHit(2));
    }

    @Test
    void registerHitIgnoresInvalidPosition() {
        smallBoat.addPosition(new Position(5,5));
        smallBoat.registerHit(new Position(8,8));
        assertFalse(smallBoat.isPartHit(0));
        assertFalse(smallBoat.isSunk());
    }

    @Test
    void boatSinksOnlyWhenAllPartsHit() {
        for (int i = 0; i < bigBoat.getSize(); i++)
            bigBoat.addPosition(new Position(i, 0));
        for (int i = 0; i < bigBoat.getSize(); i++) {
            bigBoat.registerHit(new Position(i, 0));
            if (i < bigBoat.getSize() - 1)
                assertFalse(bigBoat.isSunk());
        }
        assertTrue(bigBoat.isSunk());
    }

}
