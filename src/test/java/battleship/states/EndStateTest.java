package battleship.states;

import battleship.controller.Controller;
import battleship.controller.game.EndController;
import battleship.states.game.EndState;
import battleship.viewer.Viewer;
import battleship.viewer.game.EndViewer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndStateTest {

    @Test
    void getViewerReturnsEndViewer() {
        EndState state = new EndState(true);

        Viewer<Boolean> viewer = state.getViewer();

        assertInstanceOf(EndViewer.class, viewer);
    }

    @Test
    void getControllerReturnsEndController() {
        EndState state = new EndState(false);

        Controller<Boolean> controller = state.getController();

        assertInstanceOf(EndController.class, controller);
    }
}