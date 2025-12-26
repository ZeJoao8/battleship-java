package battleship.states;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.viewer.Viewer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StateTest {

    private State<Object> makeState(Viewer<Object> v, Controller<Object> c) {
        return new State<>(new Object()) {
            @Override protected Viewer<Object> getViewer() { return v; }
            @Override protected Controller<Object> getController() { return c; }
        };
    }

    @Test
    void drawsIfStateUnchanged() throws Exception {
        Viewer<Object> viewer = mock(Viewer.class);
        Controller<Object> controller = mock(Controller.class);
        GUI gui = mock(GUI.class);
        Game game = mock(Game.class);

        State<Object> state = makeState(viewer, controller);

        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        doReturn(state).when(game).getState();
        doNothing().when(controller).step(game, GUI.ACTION.NONE);

        state.step(game, gui);

        verify(viewer).draw(gui);
    }

    @Test
    void doesNotDrawIfStateChanges() throws Exception {
        Viewer<Object> viewer = mock(Viewer.class);
        Controller<Object> controller = mock(Controller.class);
        GUI gui = mock(GUI.class);
        Game game = mock(Game.class);

        State<Object> oldState = makeState(viewer, controller);
        State<Object> newState = makeState(mock(Viewer.class), mock(Controller.class));

        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        doReturn(oldState, newState).when(game).getState();
        doNothing().when(controller).step(game, GUI.ACTION.NONE);

        oldState.step(game, gui);

        verify(viewer, never()).draw(gui);
    }

}
