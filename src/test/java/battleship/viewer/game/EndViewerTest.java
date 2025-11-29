package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EndViewerTest {

    @Test
    void drawsWinScreenCentered() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);
        EndViewer viewer = new EndViewer(true);
        viewer.drawElements(gui);
        String s1 = "▗▖  ▗▖▗▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▄▖▗▄▄▖ ▄   ▄";
        int col = (80 - s1.length())/2;
        verify(gui).drawText(new Position(col, 6),     s1, "#FFFFFF");
        verify(gui).drawText(new Position(col, 7),     "▐▌  ▐▌▐▌▐▌     █  ▐▌  ▐▌▐▌ ▐▌ ▀▄▀ ", "#FFFFFF");
        verify(gui).drawText(new Position(col, 8),     "▐▌  ▐▌▐▌▐▌     █  ▐▌  ▐▌▐▛▀▚▖  █  ", "#FFFFFF");
        verify(gui).drawText(new Position(col, 9),     " ▝▚▞▘ ▐▌▐▙▄▄▖  █  ▐▙▄▄▟▌▐▌ ▐▌  █  ", "#FFFFFF");
        verify(gui).drawText(
                new Position((80 - "Press Q to return to menu".length())/2, 11),
                "Press Q to return to menu", "#AAAAAA"
        );
    }


    @Test
    void drawsLoseScreenCentered() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);
        EndViewer viewer = new EndViewer(false);
        viewer.drawElements(gui);
        String s1 = "▗▄▄▖ ▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖ ▗▄▖ ▗▄▄▄▖";
        int col = (80 - s1.length())/2;
        verify(gui).drawText(new Position(col, 6),     s1, "#FFFFFF");
        verify(gui).drawText(new Position(col, 7),     "▐▌ ▐▌▐▌   ▐▌   ▐▌   ▐▌ ▐▌  █  ", "#FFFFFF");
        verify(gui).drawText(new Position(col, 8),     "▐▌ ▐▌▐▛▀  ▐▛▀▀▘▐▛▀  ▐▛▀▜▌  █  ", "#FFFFFF");
        verify(gui).drawText(new Position(col, 9),     "▐▙▄▞▘▐▙▄▄▖▐▌   ▐▙▄▄▖▐▌ ▐▌  █  ", "#FFFFFF");
        verify(gui).drawText(
                new Position((80 - "Press Q to return to menu".length())/2, 11),
                "Press Q to return to menu", "#AAAAAA"
        );
    }
}
