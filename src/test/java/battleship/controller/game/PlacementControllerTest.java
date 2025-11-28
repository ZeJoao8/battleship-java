package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Boat;
import battleship.model.game.common.Board;
import battleship.states.MenuState;
import battleship.states.State;
import battleship.states.game.BattleState;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlacementControllerTest {

    @Provide Arbitrary<GUI.ACTION> movementActions() {
        return Arbitraries.of(GUI.ACTION.UP, GUI.ACTION.DOWN, GUI.ACTION.LEFT, GUI.ACTION.RIGHT);
    }

    @Property
    void movementCallsCorrectModelMethod(@ForAll("movementActions") GUI.ACTION action) {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        when(model.getCurrentBoat()).thenReturn(mock(Boat.class));
        PlacementController controller = new PlacementController(model, settings);
        controller.step(null, action);
        switch(action) {
            case UP    -> verify(model).moveUp();
            case DOWN  -> verify(model).moveDown();
            case LEFT  -> verify(model).moveLeft();
            case RIGHT -> verify(model).moveRight();
        }
    }

    @Test
    void rotateRotatesBoat() {
        PlacementModel m = mock(PlacementModel.class);
        when(m.getCurrentBoat()).thenReturn(mock(Boat.class));
        new PlacementController(m, mock(Settings.class)).step(null, GUI.ACTION.ROTATE);
        verify(m).rotateCurrentBoat();
    }

    @Test
    void select_placesBoat_ifNotAllPlaced() {
        PlacementModel m = mock(PlacementModel.class);
        when(m.getCurrentBoat()).thenReturn(mock(Boat.class));
        when(m.allBoatsPlaced()).thenReturn(false);
        new PlacementController(m, mock(Settings.class)).step(null, GUI.ACTION.SELECT);
        verify(m).placeCurrentBoat();
        verify(m).allBoatsPlaced();
        verifyNoInteractions(mock(Game.class));
    }

    @Test
    void select_startsBattleOnlyWhenAllPlaced() {
        Board board = mock(Board.class);
        PlacementModel m = mock(PlacementModel.class);
        when(m.getCurrentBoat()).thenReturn(mock(Boat.class));
        when(m.allBoatsPlaced()).thenReturn(true);
        when(m.getBoard()).thenReturn(board);
        Game g = mock(Game.class);
        new PlacementController(m, new Settings()).step(g, GUI.ACTION.SELECT);
        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);
        verify(g).setState(captor.capture());
        assertInstanceOf(BattleState.class, captor.getValue());
    }

    @Test
    void quit_goesToMenu() {
        PlacementModel m = mock(PlacementModel.class);
        when(m.getCurrentBoat()).thenReturn(mock(Boat.class));
        Game g = mock(Game.class);
        new PlacementController(m, mock(Settings.class)).step(g, GUI.ACTION.QUIT);
        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);
        verify(g).setState(captor.capture());
        assertInstanceOf(MenuState.class, captor.getValue());
    }

    @Test
    void noCurrentBoat_startsBattleImmediately() {
        PlacementModel m = mock(PlacementModel.class);
        when(m.getCurrentBoat()).thenReturn(null);
        when(m.getBoard()).thenReturn(mock(Board.class));
        Game g = mock(Game.class);
        new PlacementController(m, new Settings()).step(g, GUI.ACTION.UP);
        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);
        verify(g).setState(captor.capture());
        assertInstanceOf(BattleState.class, captor.getValue());
    }
}
