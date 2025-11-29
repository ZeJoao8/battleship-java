package battleship.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    @Test
    void entriesReturnedInCorrectOrder() {
        Menu menu = new Menu();
        assertEquals("Play", menu.getEntry(0));
        assertEquals("Settings", menu.getEntry(1));
        assertEquals("Quit", menu.getEntry(2));
    }

    @Test
    void numberOfEntriesIsThree() {
        assertEquals(3, new Menu().getNumberEntries());
    }

    @Test
    void selectionMovesForwardCorrectly() {
        Menu menu = new Menu();
        assertTrue(menu.isSelectedPlay());
        menu.next(); assertTrue(menu.isSelectedSettings());
        menu.next(); assertTrue(menu.isSelectedQuit());
        menu.next(); assertTrue(menu.isSelectedPlay());
    }

    @Test
    void selectionMovesBackwardCorrectly() {
        Menu menu = new Menu();
        menu.previous(); assertTrue(menu.isSelectedQuit());
        menu.previous(); assertTrue(menu.isSelectedSettings());
        menu.previous(); assertTrue(menu.isSelectedPlay());
    }

    @Test
    void isSelectedIntOnlyTrueForCurrent() {
        Menu menu = new Menu();
        assertTrue(menu.isSelected(0));
        menu.next();
        assertTrue(menu.isSelected(1));
        menu.next();
        assertTrue(menu.isSelected(2));
    }

    @Test
    void quitSelectionDetected() {
        Menu menu = new Menu();
        assertFalse(menu.isSelectedQuit());
        menu.next(); menu.next();
        assertTrue(menu.isSelectedQuit());
    }
}
