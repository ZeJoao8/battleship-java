package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Boat;
import battleship.model.game.common.Board;
import battleship.states.MenuState;
import battleship.states.State;
import battleship.states.game.BattleState;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class PlacementControllerTest {

    private PlacementController controllerWithCurrentBoat(PlacementModel model, Settings settings) {
        Boat boat = mock(Boat.class);
        when(model.getCurrentBoat()).thenReturn(boat);
        return new PlacementController(model, settings);
    }

    // ---------- movimentos ----------

    @Test
    void up_movesBoatUp() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.UP);

        verify(model).moveUp();
        verifyNoMoreInteractions(game);
    }

    @Test
    void down_movesBoatDown() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.DOWN);

        verify(model).moveDown();
        verifyNoMoreInteractions(game);
    }

    @Test
    void left_movesBoatLeft() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.LEFT);

        verify(model).moveLeft();
        verifyNoMoreInteractions(game);
    }

    @Test
    void right_movesBoatRight() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.RIGHT);

        verify(model).moveRight();
        verifyNoMoreInteractions(game);
    }

    @Test
    void rotate_rotatesCurrentBoat() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.ROTATE);

        verify(model).rotateCurrentBoat();
        verifyNoMoreInteractions(game);
    }

    // ---------- SELECT ----------

    @Test
    void select_placesBoat_doesNotStartBattle_ifNotAllPlaced() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        when(model.allBoatsPlaced()).thenReturn(false);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.SELECT);

        verify(model).placeCurrentBoat();
        verify(model).allBoatsPlaced();
        verify(game, never()).setState(any());
    }

    @Test
    void select_placesBoat_andStartsBattle_ifAllPlaced() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);
        Board playerBoard = mock(Board.class);

        when(model.getCurrentBoat()).thenReturn(mock(Boat.class));
        when(model.allBoatsPlaced()).thenReturn(true);
        when(model.getBoard()).thenReturn(playerBoard);

        PlacementController controller = new PlacementController(model, settings);

        controller.step(game, GUI.ACTION.SELECT);

        verify(model).placeCurrentBoat();
        verify(model).allBoatsPlaced();
        verify(model, atLeastOnce()).getBoard();

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(BattleState.class, stateCaptor.getValue());
    }

    // ---------- QUIT ----------

    @Test
    void quit_goesBackToMenu() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);

        PlacementController controller = controllerWithCurrentBoat(model, settings);

        controller.step(game, GUI.ACTION.QUIT);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(MenuState.class, stateCaptor.getValue());
    }

    // ---------- current == null ----------

    @Test
    void whenNoCurrentBoat_startBattleImmediately() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = mock(Settings.class);
        Game game = mock(Game.class);
        Board playerBoard = mock(Board.class);

        when(model.getCurrentBoat()).thenReturn(null);
        when(model.getBoard()).thenReturn(playerBoard);

        PlacementController controller = new PlacementController(model, settings);

        controller.step(game, GUI.ACTION.UP);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(BattleState.class, stateCaptor.getValue());
    }
}
