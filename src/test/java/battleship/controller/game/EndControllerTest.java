package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.states.MenuState;
import battleship.states.game.EndState;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndControllerTest {

    private EndController controller(EndState s) {
        return new EndController(true,s);
    }

    @Test
    void showBoardTogglesState() throws Exception {
        EndState state = spy(new EndState(true,null));
        Game game = mock(Game.class);
        controller(state).step(game,GUI.ACTION.SHOW_BOARD);
        verify(state).toggleBoards();
    }

    @Test
    void quitReturnsToMenu() throws Exception {
        EndState state = new EndState(true,null);
        Game game = mock(Game.class);
        controller(state).step(game,GUI.ACTION.QUIT);
        verify(game).setState(any(MenuState.class));
    }

    @Test
    void unrelatedActionDoesNothing() throws Exception {
        EndState state = spy(new EndState(true,null));
        Game game = mock(Game.class);
        controller(state).step(game,GUI.ACTION.NONE);
        verify(state,never()).toggleBoards();
        verify(game,never()).setState(any());
    }

    @Property
    void anyActionDoesNotThrow(@ForAll GUI.ACTION a) {
        EndState state = new EndState(true,null);
        Game game = mock(Game.class);
        assertDoesNotThrow(() -> controller(state).step(game,a));
    }
}
