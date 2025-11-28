package battleship.viewer.game;

import battleship.gui.GUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class EndViewerTest {

    @Test
    void drawElementsWhenWon() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);

        EndViewer viewer = new EndViewer(true);

        assertDoesNotThrow(() -> viewer.drawElements(gui));
    }

    @Test
    void drawElementsWhenLost() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);

        EndViewer viewer = new EndViewer(false);

        assertDoesNotThrow(() -> viewer.drawElements(gui));
    }
}
