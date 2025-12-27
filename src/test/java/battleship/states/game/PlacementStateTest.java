package battleship.states.game;

import battleship.model.Settings;
import battleship.model.game.PlacementModel;
import battleship.viewer.game.placement.PlacementViewer;
import battleship.controller.game.PlacementController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlacementStateTest {

    @Test
    void gettersReturnCorrectValues() {
        PlacementModel model = mock(PlacementModel.class);
        Settings settings = new Settings();
        PlacementState state = new PlacementState(model, settings);
        assertSame(model, state.getModel());
        assertSame(settings, state.getSettings());
        assertTrue(state.getViewer() instanceof PlacementViewer);
        assertTrue(state.getController() instanceof PlacementController);
    }
}
