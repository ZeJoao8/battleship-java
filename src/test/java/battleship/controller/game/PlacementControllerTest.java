package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.states.MenuState;
import battleship.states.game.BattleState;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlacementControllerTest {

    private PlacementController controller(PlacementModel m) {
        return new PlacementController(m,new Settings());
    }

    private PlacementModel model() {
        return new PlacementModel(new Board(new Settings(10,10, Settings.BotDifficulty.EASY)));
    }

    @Test
    void movementActionsCallModel() throws Exception {
        PlacementModel m = spy(model());
        Game g = mock(Game.class);
        controller(m).step(g,GUI.ACTION.UP);
        controller(m).step(g,GUI.ACTION.DOWN);
        controller(m).step(g,GUI.ACTION.LEFT);
        controller(m).step(g,GUI.ACTION.RIGHT);
        verify(m).moveUp();
        verify(m).moveDown();
        verify(m).moveLeft();
        verify(m).moveRight();
    }

    @Test
    void rotateCallsModel() throws Exception {
        PlacementModel m = spy(model());
        Game g = mock(Game.class);
        controller(m).step(g,GUI.ACTION.ROTATE);
        verify(m).rotateCurrentBoat();
    }

    @Test
    void autoPlaceCallsModel() throws Exception {
        PlacementModel m = spy(model());
        Game g = mock(Game.class);
        controller(m).step(g,GUI.ACTION.AUTO);
        verify(m).autoPlaceFleet();
    }

    @Test
    void undoCallsModel() throws Exception {
        PlacementModel m = spy(model());
        Game g = mock(Game.class);
        controller(m).step(g,GUI.ACTION.UNDO);
        verify(m).undoLastBoat();
    }

    @Test
    void selectPlacesBoatWhenNotFinished() throws Exception {
        PlacementModel m = spy(model());
        Game g = mock(Game.class);
        when(m.allBoatsPlaced()).thenReturn(false);
        controller(m).step(g,GUI.ACTION.SELECT);
        verify(m).placeCurrentBoat();
        verify(g,never()).setState(any());
    }

    @Test
    void selectStartsBattleWhenAllPlaced() throws Exception {
        PlacementModel m = spy(model());
        Game g = mock(Game.class);
        when(m.allBoatsPlaced()).thenReturn(true);
        controller(m).step(g,GUI.ACTION.SELECT);
        verify(g).setState(any(BattleState.class));
    }

    @Test
    void quitReturnsToMenu() throws Exception {
        PlacementModel m = model();
        Game g = mock(Game.class);
        controller(m).step(g,GUI.ACTION.QUIT);
        verify(g).setState(any(MenuState.class));
    }

    @Property
    void anyActionDoesNotThrow(@ForAll GUI.ACTION a) {
        PlacementModel m = model();
        Game g = mock(Game.class);
        assertDoesNotThrow(() -> controller(m).step(g,a));
    }
}
