package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.Settings.BotDifficulty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class SettingsViewerTest {

    @Test
    void drawElements_doesNotThrow() {
        GUI gui = mock(GUI.class);

        Settings settings = new Settings();
        settings.setWidth(10);
        settings.setHeight(10);
        settings.setDifficulty(BotDifficulty.HUNT);
        settings.setSelector(1);

        SettingsViewer viewer = new SettingsViewer(settings);

        assertDoesNotThrow(() -> viewer.drawElements(gui));
    }

    @Test
    void drawElements_selectorVariants_doNotThrow() {
        GUI gui = mock(GUI.class);

        for (int selector = 0; selector < 3; selector++) {
            Settings settings = new Settings();
            settings.setWidth(8);
            settings.setHeight(8);
            settings.setDifficulty(BotDifficulty.RANDOM);
            settings.setSelector(selector);

            SettingsViewer viewer = new SettingsViewer(settings);

            assertDoesNotThrow(() -> viewer.drawElements(gui));
        }
    }
}
