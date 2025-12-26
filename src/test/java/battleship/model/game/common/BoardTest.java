package battleship.model.game.common;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.boats.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;

    @BeforeEach
    void setup() {
        board = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
    }

    @Test
    void gridStartsFilledWithWater() {
        for (int x=0;x<10;x++)
            for (int y=0;y<10;y++)
                assertTrue(board.isWater(new Position(x,y)));
    }

    @Test
    void fleetContainsCorrectBoatComposition() {
        List<Boat> f = board.getFleet();
        assertEquals(5,f.size());
        int portaAvioes=0, navio=0, contraTorp=0, submarino=0;
        for(Boat b : f){
            String name = b.getName();
            if(name.equals("Porta-Aviões")) portaAvioes++;
            else if(name.equals("Navio Tanque")) navio++;
            else if(name.equals("Contra Torpedeiro")) contraTorp++;
            else if(name.equals("Submarino")) submarino++;
        }
        assertEquals(1, portaAvioes);
        assertEquals(1, navio);
        assertEquals(2, contraTorp);
        assertEquals(1, submarino);
    }

    @Test
    void canPlaceBoatReturnsTrueAndFalseCorrectly() {
        Boat b = new Battleship();
        assertTrue(board.canPlaceBoat(b,new Position(2,2),true));
        assertFalse(board.canPlaceBoat(b,new Position(9,9),true));
        board.placeBoat(b,new Position(2,2),true);
        assertFalse(board.canPlaceBoat(new Destroyer(),new Position(2,2),true));
    }

    @Test
    void placeBoatMarksShipCellsOrIgnoresInvalidPlacement() {
        Boat b = new Battleship();
        board.placeBoat(b,new Position(1,5),true);
        assertTrue(board.isShip(new Position(1,5)));
        assertTrue(board.isShip(new Position(2,5)));
        assertTrue(board.isShip(new Position(3,5)));
        Board b2 = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
        b2.placeBoat(b,new Position(9,9),true); // invalid
        assertTrue(b2.isWater(new Position(9,9)));
    }

    @Test
    void placingBoatAgainRestoresPreviousCellsBackToWater() {
        Boat boat = board.getFleet().get(4);
        board.placeBoat(boat,new Position(0,0),true);
        board.placeBoat(boat,new Position(5,5),true);
        assertTrue(board.isWater(new Position(0,0)));
        assertTrue(board.isShip(new Position(5,5)));
    }

    @Test
    void hitAndMissAreRecordedCorrectly() {
        Position miss = new Position(3,3);
        assertFalse(board.isHit(miss));
        assertFalse(board.isMiss(miss));
        board.markMiss(miss);
        assertTrue(board.isMiss(miss));
        assertFalse(board.isHit(miss));
        Boat b = new Destroyer();
        board.placeBoat(b,new Position(4,1),true);
        Position hp = b.getPositions().get(0);
        board.markHit(hp);
        assertTrue(board.isHit(hp));
        assertFalse(board.isMiss(hp));
    }

    @Test
    void validBotTargetAllowsWaterOrUnhitShipOnly() {
        Position p = new Position(5,5);
        assertTrue(board.isValidBotTarget(p));
        Boat sub = new Destroyer();
        board.placeBoat(sub,p,true);
        assertTrue(board.isValidBotTarget(p));
        board.markHit(p);
        assertFalse(board.isValidBotTarget(p));
    }

    @Test
    void isInsideReturnsTrueOnlyForValidCoordinates() {
        assertTrue(board.isInside(new Position(0,0)));
        assertTrue(board.isInside(new Position(9,9)));
        assertFalse(board.isInside(new Position(-1,0)));
        assertFalse(board.isInside(new Position(10,0)));
        assertFalse(board.isInside(new Position(0,-1)));
        assertFalse(board.isInside(new Position(0,10)));
    }

    @Test
    void hasLostIsTrueOnlyWhenNoShipsRemain() {
        assertTrue(board.hasLost());
        board.placeFleetRandomly();
        assertFalse(board.hasLost());
        for(int x=0;x<board.getWidth();x++)
            for(int y = 0; y<board.getHeight(); y++)
                if(board.isShip(new Position(x,y)))
                    board.markHit(new Position(x,y));
        assertTrue(board.hasLost());
    }

    @Test
    void getNextUnplacedBoatReturnsNullOnlyAfterAllPlaced() {
        assertFalse(board.allBoatsPlaced());
        assertNotNull(board.getNextUnplacedBoat());
        int y=0;
        for (Boat b : board.getFleet())
            board.placeBoat(b,new Position(0,y++),true);
        assertTrue(board.allBoatsPlaced());
        assertNull(board.getNextUnplacedBoat());
    }
}
