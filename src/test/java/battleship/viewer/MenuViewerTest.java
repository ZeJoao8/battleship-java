package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class MenuViewerTest {

    @Test
    void drawElements_doesNotThrow() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);

        Menu menu = new Menu();

        menu.next();

        MenuViewer viewer = new MenuViewer(menu);

        assertDoesNotThrow(() -> viewer.drawElements(gui));
    }
}
