package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementModelTest {

    private PlacementModel model(int w, int h) {
        return new PlacementModel(new Board(new Settings(w, h, Settings.BotDifficulty.EASY)));
    }

    private PlacementModel model() {
        return model(6, 6);
    }

    @Test
    void cursorStartsAtZeroZero() {
        assertEquals(new Position(0,0), model().getCursor());
    }

    @Test
    void moveRightAndDown() {
        PlacementModel m = model();
        m.moveRight();
        m.moveDown();
        assertEquals(new Position(1,1), m.getCursor());
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
    void rotateDoesNothingWhenNoCurrentBoat() {
        PlacementModel m = model();
        m.getBoard().placeFleetRandomly();
        assertDoesNotThrow(m::rotateCurrentBoat);
    }

    @Test
    void rotateRotatesBoatWhenPresent() {
        PlacementModel m = model();
        Boat b = m.getBoard().getNextUnplacedBoat();
        boolean initial = b.isHorizontal();
        m.rotateCurrentBoat();
        assertNotEquals(initial, b.isHorizontal());
    }

    @Test
    void placeDoesNothingWhenNoCurrentBoat() {
        PlacementModel m = model();
        m.getBoard().placeFleetRandomly();
        assertDoesNotThrow(m::placeCurrentBoat);
    }

    @Test
    void placeBoatSucceedsWhenValid() {
        PlacementModel m = model();
        m.placeCurrentBoat();
        assertFalse(m.getBoard().getFleet().get(0).getPositions().isEmpty());
    }

    @Test
    void placeBoatFailsWhenInvalid() {
        PlacementModel m = model(2,2);
        m.placeCurrentBoat();
        assertFalse(m.allBoatsPlaced());
    }

    @Test
    void autoPlaceSetsAutoPlacedTrue() {
        PlacementModel m = model();
        m.autoPlaceFleet();
        assertTrue(m.wasAutoPlaced());
        assertTrue(m.allBoatsPlaced());
    }

    @Test
    void undoAfterAutoPlaceResetsFlag() {
        PlacementModel m = model();
        m.autoPlaceFleet();
        m.undoLastBoat();
        assertFalse(m.wasAutoPlaced());
    }

    @Test
    void undoWithoutAutoPlaceDoesNotCrash() {
        PlacementModel m = model();
        assertDoesNotThrow(m::undoLastBoat);
    }

    @Test
    void cursorClampedHorizontallyForHorizontalBoat() {
        PlacementModel m = model();
        Boat b = m.getBoard().getNextUnplacedBoat();
        b.setHorizontal(true);
        for (int i = 0; i < 20; i++) m.moveRight();
        assertTrue(m.getCursor().getX() <= m.getBoard().getWidth());
    }

    @Test
    void cursorClampedVerticallyForVerticalBoat() {
        PlacementModel m = model();
        Boat b = m.getBoard().getNextUnplacedBoat();
        b.setHorizontal(false);
        for (int i = 0; i < 20; i++) m.moveDown();
        assertTrue(m.getCursor().getY() <= m.getBoard().getHeight());
    }

    @Test
    void allBoatsPlacedTrueAfterAutoPlace() {
        PlacementModel m = model();
        m.autoPlaceFleet();
        assertTrue(m.allBoatsPlaced());
    }
}
