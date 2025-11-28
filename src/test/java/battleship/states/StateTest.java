package battleship.states;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.viewer.Viewer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StateTest {
    private State<?> makeState(Viewer<Object> v, Controller<Object> c){
        return new State<>(new Object()) {
            @Override protected Viewer<Object> getViewer(){ return v; }
            @Override protected Controller<Object> getController(){ return c; }
        };
    }

    @Test
    void drawsIfStateUnchanged() throws Exception {
        Viewer viewer = mock(Viewer.class);
        Controller controller = mock(Controller.class);
        GUI gui = mock(GUI.class);
        Game game = mock(Game.class);
        State<?> state = makeState(viewer, controller);
        when(game.getState()).thenReturn((State)state);
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        state.step(game, gui);
        verify(viewer).draw(gui);
    }

    @Test
    void doesNotDrawIfStateChanges() throws Exception {
        Viewer<?> viewer = mock(Viewer.class);
        Controller<?> controller = mock(Controller.class);
        GUI gui = mock(GUI.class);
        Game game = mock(Game.class);
        State<?> oldState = makeState((Viewer<Object>) viewer, (Controller<Object>) controller);
        State<?> newState = makeState(mock(Viewer.class), mock(Controller.class));
        when(gui.getNextAction()).thenReturn(GUI.ACTION.NONE);
        when(game.getState()).thenReturn((State)oldState).thenReturn(newState);
        oldState.step(game, gui);
        verify(viewer, never()).draw(gui);
    }
}
