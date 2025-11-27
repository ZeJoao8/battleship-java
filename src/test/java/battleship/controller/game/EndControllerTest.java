package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.states.MenuState;
import battleship.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class EndControllerTest {
    @Test
    void quit_setsMenuState() {
        Game game = mock(Game.class);
        EndController controller = new EndController(true);

        controller.step(game, GUI.ACTION.QUIT);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(MenuState.class, stateCaptor.getValue());
    }

    @Test
    void select_setsMenuState() {
        Game game = mock(Game.class);
        EndController controller = new EndController(false);

        controller.step(game, GUI.ACTION.SELECT);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(MenuState.class, stateCaptor.getValue());
    }

    @Test
    void otherActions_doNothing() {
        Game game = mock(Game.class);
        EndController controller = new EndController(true);

        controller.step(game, GUI.ACTION.UP);
        controller.step(game, GUI.ACTION.DOWN);
        controller.step(game, GUI.ACTION.LEFT);
        controller.step(game, GUI.ACTION.RIGHT);
        controller.step(game, GUI.ACTION.ROTATE);

        verify(game, never()).setState(any());
    }
}
