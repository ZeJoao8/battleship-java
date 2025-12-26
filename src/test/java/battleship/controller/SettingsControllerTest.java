package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.MenuState;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsControllerTest {

    private SettingsController controller(Settings s) {
        return new SettingsController(s);
    }

    @Test
    void upAndDownChangeSelector() {
        Settings s = new Settings();
        Game g = mock(Game.class);

        int start = s.getSelector();
        controller(s).step(g,GUI.ACTION.DOWN);
        assertEquals((start+1)%3,s.getSelector());

        controller(s).step(g,GUI.ACTION.UP);
        assertEquals(start,s.getSelector());
    }

    @Test
    void leftRightChangeWidthHeightAndDifficulty() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.EASY);
        Game g = mock(Game.class);

        s.setSelector(0);
        controller(s).step(g,GUI.ACTION.RIGHT);
        assertEquals(11,s.getWidth());

        controller(s).step(g,GUI.ACTION.LEFT);
        assertEquals(10,s.getWidth());

        s.setSelector(1);
        controller(s).step(g,GUI.ACTION.RIGHT);
        assertEquals(11,s.getHeight());

        controller(s).step(g,GUI.ACTION.LEFT);
        assertEquals(10,s.getHeight());

        s.setSelector(2);
        controller(s).step(g,GUI.ACTION.RIGHT);
        assertEquals(Settings.BotDifficulty.MEDIUM,s.getDifficulty());

        controller(s).step(g,GUI.ACTION.LEFT);
        assertEquals(Settings.BotDifficulty.EASY,s.getDifficulty());
    }

    @Test
    void quitAndSelectReturnToMenu() {
        Settings s = new Settings();
        Game g = mock(Game.class);

        controller(s).step(g,GUI.ACTION.QUIT);
        verify(g).setState(any(MenuState.class));

        controller(s).step(g,GUI.ACTION.SELECT);
        verify(g,times(2)).setState(any(MenuState.class));
    }

    @Property
    void anyActionNeverThrows(@ForAll GUI.ACTION a) {
        Settings s = new Settings();
        Game g = mock(Game.class);
        assertDoesNotThrow(() -> controller(s).step(g,a));
    }
}
