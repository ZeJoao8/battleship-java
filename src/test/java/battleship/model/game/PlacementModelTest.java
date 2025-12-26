package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementModelTest {

    private PlacementModel model() {
        return new PlacementModel(new Board(new Settings(6,6, Settings.BotDifficulty.EASY)));
    }

    @Test
    void cursorStartsAtZeroZero() {
        assertEquals(new Position(0,0), model().getCursor());
    }

    @Test
    void moveRightKeepsCursorInsideBoard() {
        PlacementModel m = model();
        m.moveRight();
        assertTrue(m.getCursor().getX() >= 0);
    }

    @Test
    void moveDownKeepsCursorInsideBoard() {
        PlacementModel m = model();
        m.moveDown();
        assertTrue(m.getCursor().getY() >= 0);
    }

    @Test
    void moveLeftAtZeroDoesNotGoNegative() {
        PlacementModel m = model();
        m.moveLeft();
        assertEquals(0, m.getCursor().getX());
    }

    @Test
    void moveUpAtZeroDoesNotGoNegative() {
        PlacementModel m = model();
        m.moveUp();
        assertEquals(0, m.getCursor().getY());
    }

    @Test
    void autoPlacedDefaultsToFalse() {
        assertFalse(model().wasAutoPlaced());
    }
}
