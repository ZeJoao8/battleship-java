package battleship.model;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {
    @Test
    void defaultSettingsAreCorrect() {
        Settings s = new Settings();
        assertEquals(10, s.getWidth());
        assertEquals(10, s.getHeight());
        assertEquals(Settings.BotDifficulty.EASY, s.getDifficulty());
        assertEquals(0, s.getSelector());
    }

    @Test
    void parameterizedConstructorSetsValuesCorrectly() {
        Settings s = new Settings(12, 8, Settings.BotDifficulty.HARD);
        assertEquals(12, s.getWidth());
        assertEquals(8, s.getHeight());
        assertEquals(Settings.BotDifficulty.HARD, s.getDifficulty());
    }

    @Property
    void setWidthOnlyAcceptsPositiveValues(@ForAll int w) {
        Settings s = new Settings();
        int original = s.getWidth();
        s.setWidth(w);
        if (w > 0) {
            assertEquals(w, s.getWidth());
        } else {
            assertEquals(original, s.getWidth());
        }
    }

    @Property
    void setHeightOnlyAcceptsPositiveValues(@ForAll int h) {
        Settings s = new Settings();
        int original = s.getHeight();
        s.setHeight(h);
        if (h > 0) {
            assertEquals(h, s.getHeight());
        } else {
            assertEquals(original, s.getHeight());
        }
    }

    @Property
    void selectorIsAlwaysBetweenZeroAndTwo(@ForAll int sel) {
        Settings s = new Settings();
        s.setSelector(sel);
        assertTrue(s.getSelector() >= 0);
        assertTrue(s.getSelector() <= 2);
    }

    @Property
    void selectorWrapsCorrectlyUsingModulo(@ForAll int sel) {
        Settings s = new Settings();
        s.setSelector(sel);
        int expected = ((sel % 3) + 3) % 3;
        assertEquals(expected, s.getSelector());
    }

    @Test
    void difficultyCanBeChanged() {
        Settings s = new Settings();
        s.setDifficulty(Settings.BotDifficulty.MEDIUM);
        assertEquals(Settings.BotDifficulty.MEDIUM, s.getDifficulty());
        s.setDifficulty(Settings.BotDifficulty.IMPOSSIBLE);
        assertEquals(Settings.BotDifficulty.IMPOSSIBLE, s.getDifficulty());
    }

    @Property
    void settingsRemainConsistentAfterRandomOperations(
            @ForAll int w,
            @ForAll int h,
            @ForAll int sel,
            @ForAll Settings.BotDifficulty diff
    ) {
        Settings s = new Settings();
        s.setWidth(w);
        s.setHeight(h);
        s.setSelector(sel);
        s.setDifficulty(diff);
        assertTrue(s.getWidth() > 0);
        assertTrue(s.getHeight() > 0);
        assertTrue(s.getSelector() >= 0 && s.getSelector() <= 2);
        assertEquals(diff, s.getDifficulty());
    }
}
