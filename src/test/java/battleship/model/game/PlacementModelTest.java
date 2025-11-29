package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.Settings.BotDifficulty;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlacementModelTest {

    Board board;
    PlacementModel model;

    @BeforeEach
    void setup() {
        board = new Board(new Settings(10,10, BotDifficulty.RANDOM));
        model = new PlacementModel(board);
    }

    @Test
    void initialCursorIsZeroZero() {
        assertEquals(new Position(0,0), model.getCursor());
    }

    @Test
    void getBoardReturnsInjectedInstance() {
        assertSame(board, model.getBoard());
    }

    @Test
    void horizontalBoatClampsRightCorrectly() {
        Boat b = model.getCurrentBoat();
        model.setCursor(new Position(9,0));
        model.moveRight();
        assertEquals(board.getWidth() - b.getSize(), model.getCursor().getX());
    }

    @Test
    void verticalBoatClampsXCorrectly() {
        Boat b = model.getCurrentBoat();
        b.rotate(); // becomes vertical
        model.setCursor(new Position(99,0));
        model.moveRight();
        assertEquals(board.getWidth() - 1, model.getCursor().getX());
    }

    @Test
    void verticalBoatClampsYCorrectly() {
        Boat b = model.getCurrentBoat();
        b.rotate();
        model.setCursor(new Position(0,99));
        model.moveDown();
        assertEquals(board.getHeigth() - b.getSize(), model.getCursor().getY());
    }

    @Test
    void movementWorksNormallyInsideBoard() {
        model.setCursor(new Position(5,5));
        model.moveUp();    assertEquals(new Position(5,4), model.getCursor());
        model.moveLeft();  assertEquals(new Position(4,4), model.getCursor());
        model.moveDown();  assertEquals(new Position(4,5), model.getCursor());
        model.moveRight(); assertEquals(new Position(5,5), model.getCursor());
    }

    @Test
    void successfulPlacementAdvancesToNextBoat() {
        Boat b = model.getCurrentBoat();
        model.placeCurrentBoat();
        assertTrue(b.isPlaced());
        assertNotSame(b, model.getCurrentBoat());
    }

    @Test
    void invalidPlacementDoesNotAdvanceBoat() {
        model.setCursor(new Position(9,9));
        Boat b = model.getCurrentBoat();
        model.placeCurrentBoat();
        assertFalse(b.isPlaced());
        assertSame(b, model.getCurrentBoat());
    }

    @Test
    void rotationAdjustsCursorIfOutOfBounds() {
        Boat b = model.getCurrentBoat();
        model.setCursor(new Position(9,5));
        model.rotateCurrentBoat();
        assertEquals(
                new Position(9, Math.min(5, board.getHeigth()-b.getSize())),
                model.getCursor()
        );
    }

    @Test
    void rotateAndPlaceDoesNothingWhenFleetComplete() {
        int y = 0;
        while (model.getCurrentBoat() != null) {
            model.setCursor(new Position(0, y));
            model.placeCurrentBoat();
            y++;
        }
        assertNull(model.getCurrentBoat());
        assertTrue(model.allBoatsPlaced());
        model.placeCurrentBoat();
        model.rotateCurrentBoat();
        assertNull(model.getCurrentBoat());
        assertTrue(model.allBoatsPlaced());
    }

    @Test
    void detectsWhenAllBoatsPlaced() {
        assertFalse(model.allBoatsPlaced());
        int y=0;
        while(model.getCurrentBoat()!=null){
            model.setCursor(new Position(0,y));
            model.placeCurrentBoat();
            y++;
        }
        assertTrue(model.allBoatsPlaced());
    }
}
