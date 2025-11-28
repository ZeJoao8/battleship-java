package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class PlacementModelTest {

    Board board;
    PlacementModel model;

    @BeforeEach
    void setup() {
        board = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
        model = new PlacementModel(board);
    }

    @Test
    void getBoardWorks() {
        assertSame(board, model.getBoard());
    }

    @Test
    void getCursorInitial() {
        assertEquals(0, model.getCursor().getX());
        assertEquals(0, model.getCursor().getY());
    }

    @Test
    void cursorNeverLeavesHorizontalBounds() {
        Boat b = model.getCurrentBoat();
        model.setCursor(new Position(9,0));
        model.moveRight();
        assertEquals(board.getWidth()-b.getSize(), model.getCursor().getX());
    }

    @Test
    void verticalClampXTooLarge() {
        Boat b = model.getCurrentBoat();
        b.rotate(); // vertical
        model.setCursor(new Position(99,0));
        model.moveRight();
        assertEquals(board.getWidth()-1, model.getCursor().getX());
    }

    @Test
    void verticalClampYTooLarge() {
        Boat b = model.getCurrentBoat();
        b.rotate(); // vertical
        model.setCursor(new Position(0,99));
        model.moveDown();
        assertEquals(board.getHeigth()-b.getSize(), model.getCursor().getY());
    }

    @Test
    void movesNormallyInsideBounds() {
        model.setCursor(new Position(5,5));
        model.moveUp();    assertEquals(4, model.getCursor().getY());
        model.moveLeft();  assertEquals(4, model.getCursor().getX());
        model.moveDown();  assertEquals(5, model.getCursor().getY());
        model.moveRight(); assertEquals(5, model.getCursor().getX());
    }

    @Test
    void validPlacementAdvancesToNextBoat() {
        Boat b = model.getCurrentBoat();
        model.placeCurrentBoat();
        assertTrue(b.isPlaced());
        assertNotSame(b, model.getCurrentBoat());
    }

    @Test
    void invalidPlacementDoesNotAdvance() {
        model.setCursor(new Position(9,9));
        Boat b = model.getCurrentBoat();
        model.placeCurrentBoat();
        assertFalse(b.isPlaced());
        assertSame(b, model.getCurrentBoat());
    }

    @Test
    void rotationClampsCorrectly() {
        Boat b = model.getCurrentBoat();
        model.setCursor(new Position(9,5));
        model.rotateCurrentBoat();
        assertEquals(Math.min(5, board.getHeigth()-b.getSize()), model.getCursor().getY());
    }

    @Test
    void rotateDoesNothingIfNoBoatsLeft() {
        while(model.getCurrentBoat()!=null){
            model.setCursor(new Position(0, model.getCursor().getY()+1));
            model.placeCurrentBoat();
        }
        assertNull(model.getCurrentBoat());
        model.placeCurrentBoat();
        model.rotateCurrentBoat();
        assertNull(model.getCurrentBoat());
    }


    @Test
    void allBoatsPlacedTrueAndFalse() {
        assertFalse(model.allBoatsPlaced());
        int y=0;
        for(Boat b : board.getFleet()){
            model.setCursor(new Position(0,y++));
            model.placeCurrentBoat();
        }
        assertTrue(model.allBoatsPlaced());
    }
}
