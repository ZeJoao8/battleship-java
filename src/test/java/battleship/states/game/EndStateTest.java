package battleship.states.game;

import battleship.model.game.GameStats;
import battleship.viewer.game.EndViewer;
import battleship.controller.game.EndController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndStateTest {

    @Test
    void toggleBoardsWorks() {
        GameStats stats = mock(GameStats.class);
        EndState state = new EndState(true, stats);
        assertFalse(state.isShowingBoards());
        state.toggleBoards();
        assertTrue(state.isShowingBoards());
        state.toggleBoards();
        assertFalse(state.isShowingBoards());
    }

    @Test
    void gettersReturnCorrectTypes() {
        GameStats stats = mock(GameStats.class);
        EndState state = new EndState(false, stats);
        assertTrue(state.getViewer() instanceof EndViewer);
        assertTrue(state.getController() instanceof EndController);
    }
}
