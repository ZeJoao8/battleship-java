package battleship.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    @Test
    void getEntryReturnsCorrectNames() {
        Menu menu = new Menu();

        assertEquals("Play", menu.getEntry(0));
        assertEquals("Settings", menu.getEntry(1));
        assertEquals("Quit", menu.getEntry(2));
    }

    @Test
    void getNumberEntriesIsAlwaysThree() {
        Menu menu = new Menu();
        assertEquals(3, menu.getNumberEntries());
    }

    @Test
    void isSelectedIntReturnsTrueOnlyForCurrentIndex() {
        Menu menu = new Menu();

        assertTrue(menu.isSelected(0));
        assertFalse(menu.isSelected(1));
        assertFalse(menu.isSelected(2));

        menu.next();
        assertTrue(menu.isSelected(1));
        assertFalse(menu.isSelected(0));
        assertFalse(menu.isSelected(2));

        menu.next();
        assertTrue(menu.isSelected(2));
        assertFalse(menu.isSelected(0));
        assertFalse(menu.isSelected(1));
    }

    @Test
    void isSelectedQuit() {
        Menu menu = new Menu();
        assertFalse(menu.isSelectedQuit());
        menu.next();
        menu.next();
        assertTrue(menu.isSelectedQuit());
    }

    @Test
    void nextCyclesInOrder() {
        Menu menu = new Menu();
        assertTrue(menu.isSelectedPlay());
        menu.next(); assertTrue(menu.isSelectedSettings());
        menu.next(); assertTrue(menu.isSelectedQuit());
        menu.next(); assertTrue(menu.isSelectedPlay());
    }

    @Test
    void previousCyclesBackwards() {
        Menu menu = new Menu();
        menu.previous(); assertTrue(menu.isSelectedQuit());
        menu.previous(); assertTrue(menu.isSelectedSettings());
        menu.previous(); assertTrue(menu.isSelectedPlay());
    }
}


