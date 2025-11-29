package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Position;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MenuViewerTest {

    @Test
    void drawsMenuTitleAndOptionsCentered() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);
        Menu menu = new Menu();
        MenuViewer viewer = new MenuViewer(menu);
        viewer.drawElements(gui);
        String s1 = "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▖ ▗▖   ▗▖ ▗▖ ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖   ";
        int col = (80 - s1.length())/2;
        verify(gui).drawText(new Position(col,4),     s1, "#FFFFFF");
        verify(gui).drawText(new Position(col,5),     "▐▌ ▐▌▐▌ ▐▌ █ ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌    ▐▛▚▖▐▌▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▌   ", "#FFFFFF");
        verify(gui).drawText(new Position(col,6),     "▐▛▀▚▖▐▛▀▜▌ █ ▐▛▀▜▌▐▌   ▐▛▀▜▌▐▛▀▜▌    ▐▌ ▝▜▌▐▛▀▜▌▐▌  ▐▌▐▛▀▜▌▐▌   ", "#FFFFFF");
        verify(gui).drawText(new Position(col,7),     "▐▙▄▞▘▐▌ ▐▌ █ ▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌ ▝▚▞▘ ▐▌ ▐▌▐▙▄▄▖", "#FFFFFF");
        verify(gui).drawText(any(Position.class), eq("> Play <"), eq("#00FF9D"));
        verify(gui).drawText(any(Position.class), eq("Settings"), eq("#FFFFFF"));
        verify(gui).drawText(any(Position.class), eq("Quit"), eq("#FFFFFF"));
    }

    @Test
    void selectionMovesAndViewerReflectsIt() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(80);
        Menu menu = new Menu();
        MenuViewer viewer = new MenuViewer(menu);
        menu.next();
        viewer.drawElements(gui);
        verify(gui).drawText(any(Position.class), eq("> Settings <"), eq("#00FF9D"));
        verify(gui).drawText(any(Position.class), eq("Play"),      eq("#FFFFFF"));
        verify(gui).drawText(any(Position.class), eq("Quit"),      eq("#FFFFFF"));
    }
}
