package battleship.model;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    void menuHasThreeEntries() {
        Menu menu = new Menu();
        assertEquals(3, menu.getNumberEntries());
    }

    @Test
    void initialSelectionIsPlay() {
        Menu menu = new Menu();
        assertTrue(menu.isSelectedPlay());
        assertFalse(menu.isSelectedSettings());
        assertFalse(menu.isSelectedQuit());
    }

    @Test
    void entriesAreCorrect() {
        Menu menu = new Menu();
        assertEquals("Play", menu.getEntry(0));
        assertEquals("Settings", menu.getEntry(1));
        assertEquals("Quit", menu.getEntry(2));
    }

    @Property
    void nextCyclesCorrectly(@ForAll @IntRange(min = 0, max = 100) int steps) {
        Menu menu = new Menu();
        for (int i = 0; i < steps; i++)
            menu.next();
        int expected = steps % menu.getNumberEntries();
        assertTrue(menu.isSelected(expected));
    }

    @Property
    void previousCyclesCorrectly(@ForAll @IntRange(min = 0, max = 100) int steps) {
        Menu menu = new Menu();
        for (int i = 0; i < steps; i++)
            menu.previous();
        int size = menu.getNumberEntries();
        int expected = ((0 - steps) % size + size) % size;
        assertTrue(menu.isSelected(expected));
    }

    @Property
    void exactlyOneEntryIsSelectedAfterAnyNavigation(
            @ForAll @IntRange(min = 0, max = 50) int nextSteps,
            @ForAll @IntRange(min = 0, max = 50) int prevSteps
    ) {
        Menu menu = new Menu();
        for (int i = 0; i < nextSteps; i++) menu.next();
        for (int i = 0; i < prevSteps; i++) menu.previous();
        int selectedCount = 0;
        for (int i = 0; i < menu.getNumberEntries(); i++) {
            if (menu.isSelected(i)) selectedCount++;
        }
        assertEquals(1, selectedCount);
    }

    @Property
    void convenienceMethodsMatchSelection(@ForAll @IntRange(min = 0, max = 20) int steps) {
        Menu menu = new Menu();
        for (int i = 0; i < steps; i++)
            menu.next();
        int sel = steps % 3;
        assertEquals(sel == 0, menu.isSelectedPlay());
        assertEquals(sel == 1, menu.isSelectedSettings());
        assertEquals(sel == 2, menu.isSelectedQuit());
    }
}
