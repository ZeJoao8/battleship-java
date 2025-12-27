package battleship.states;

import battleship.model.Settings;
import battleship.viewer.SettingsViewer;
import battleship.controller.SettingsController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsStateTest {

    @Test
    void gettersReturnCorrectTypes() {
        Settings settings = new Settings();
        SettingsState state = new SettingsState(settings);
        assertSame(settings, state.getModel());
        assertTrue(state.getViewer() instanceof SettingsViewer);
        assertTrue(state.getController() instanceof SettingsController);
    }
}
