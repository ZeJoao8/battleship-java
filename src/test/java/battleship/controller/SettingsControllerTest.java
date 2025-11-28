package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.states.MenuState;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SettingsControllerTest {

    @Property
    void selectorMovesCorrectly(@ForAll boolean moveUp) {
        Settings s = new Settings();
        SettingsController c = new SettingsController(s);
        int expected = moveUp ? 2 : 1;
        c.step(null, moveUp ? GUI.ACTION.UP : GUI.ACTION.DOWN);
        assertEquals(expected, s.getSelector());
    }

    @Test
    void widthIncrementsAndDecrements() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);
        s.setSelector(0);
        c.step(null, GUI.ACTION.RIGHT);
        assertEquals(11, s.getWidth());
        c.step(null, GUI.ACTION.LEFT);
        assertEquals(10, s.getWidth());
    }

    @Test
    void heightIncrementsAndDecrements() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);
        s.setSelector(1);
        c.step(null, GUI.ACTION.RIGHT);
        assertEquals(11, s.getHeight());
        c.step(null, GUI.ACTION.LEFT);
        assertEquals(10, s.getHeight());
    }

    @Test
    void difficultyCyclesCorrectly() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);
        s.setSelector(2);
        c.step(null, GUI.ACTION.RIGHT);
        assertEquals(Settings.BotDifficulty.HUNT, s.getDifficulty());
        c.step(null, GUI.ACTION.LEFT);
        assertEquals(Settings.BotDifficulty.RANDOM, s.getDifficulty());
    }

    @Test
    void selectReturnsToMenu() {
        Game game = mock(Game.class);
        new SettingsController(new Settings()).step(game, GUI.ACTION.SELECT);
        verify(game).setState(isA(MenuState.class));
    }

    @Test
    void quitReturnsToMenu() {
        Game game = mock(Game.class);
        new SettingsController(new Settings()).step(game, GUI.ACTION.QUIT);
        verify(game).setState(isA(MenuState.class));
    }
}
