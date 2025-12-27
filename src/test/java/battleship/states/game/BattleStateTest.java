package battleship.states.game;

import battleship.model.game.BattleModel;
import battleship.states.State;
import battleship.viewer.game.battle.BattleViewer;
import battleship.controller.game.BattleController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BattleStateTest {

    @Test
    void gettersReturnCorrectTypes() {
        BattleModel model = mock(BattleModel.class);
        BattleState state = new BattleState(model);
        assertSame(model, state.getModel());
        assertTrue(state.getViewer() instanceof BattleViewer);
        assertTrue(state.getController() instanceof BattleController);
    }
}
