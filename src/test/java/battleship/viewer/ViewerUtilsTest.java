package battleship.viewer;

import battleship.gui.GUI;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ViewerUtilsTest {

    @Test
    void centerXWorks() {
        GUI gui = mock(GUI.class);
        when(gui.getWidth()).thenReturn(100);
        assertEquals(45,ViewerUtils.centerX(gui,"1234567890"));
    }

    @Test
    void centerYWorks() {
        GUI gui = mock(GUI.class);
        when(gui.getHeight()).thenReturn(40);
        assertEquals(15,ViewerUtils.centerY(gui,10));
    }
}
