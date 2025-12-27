package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Position;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MenuViewerTest {

    @Test
    void drawsAllMenuEntries() {
        GUI gui = mock(GUI.class);
        Menu menu = new Menu();
        MenuViewer viewer = new MenuViewer(menu);
        viewer.drawElements(gui);
        verify(gui, atLeastOnce()).drawText(any(Position.class), anyString(), anyString());
    }

    @Test
    void highlightsSelectedOption() {
        GUI gui = mock(GUI.class);
        Menu menu = new Menu();
        menu.next();
        MenuViewer viewer = new MenuViewer(menu);
        viewer.drawElements(gui);
        verify(gui, atLeastOnce()).drawText(any(Position.class), contains(">"), anyString());
    }
}
