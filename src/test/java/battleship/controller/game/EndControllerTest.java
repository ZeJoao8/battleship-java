package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.states.MenuState;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EndControllerTest {

    @Provide Arbitrary<GUI.ACTION> irrelevantActions() {
        return Arbitraries.of(
                GUI.ACTION.UP, GUI.ACTION.DOWN, GUI.ACTION.LEFT,
                GUI.ACTION.RIGHT, GUI.ACTION.ROTATE
        );
    }

    @Property
    void otherActionsDoNothing(@ForAll("irrelevantActions") GUI.ACTION action) {
        Game g = mock(Game.class);
        EndController controller = new EndController(true);
        controller.step(g, action);
        verify(g, never()).setState(any());
    }

    @Test
    void quitGoesToMenu() {
        Game g = mock(Game.class);
        new EndController(true).step(g, GUI.ACTION.QUIT);
        ArgumentCaptor<battleship.states.State> captor = ArgumentCaptor.forClass(battleship.states.State.class);
        verify(g).setState(captor.capture());
        assertInstanceOf(MenuState.class, captor.getValue());
    }

    @Test
    void selectGoesToMenu() {
        Game g = mock(Game.class);
        new EndController(false).step(g, GUI.ACTION.SELECT);
        ArgumentCaptor<battleship.states.State> captor = ArgumentCaptor.forClass(battleship.states.State.class);
        verify(g).setState(captor.capture());
        assertInstanceOf(MenuState.class, captor.getValue());
    }
}
