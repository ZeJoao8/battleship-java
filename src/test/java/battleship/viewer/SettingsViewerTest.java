package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.Position;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SettingsViewerTest {

    @Test
    void drawsSettingsOptions() {
        GUI gui = mock(GUI.class);
        Settings s = new Settings();
        SettingsViewer viewer = new SettingsViewer(s);

        viewer.drawElements(gui);

        verify(gui, atLeastOnce()).drawText(any(Position.class), anyString(), anyString());
    }

    @Test
    void highlightsSelectedField() {
        GUI gui = mock(GUI.class);
        Settings s = new Settings();
        s.setSelector(1);
        SettingsViewer viewer = new SettingsViewer(s);

        viewer.drawElements(gui);

        verify(gui, atLeastOnce()).drawText(any(Position.class), contains(">"), anyString());
    }
}
