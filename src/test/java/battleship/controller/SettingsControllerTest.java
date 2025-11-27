package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.states.MenuState;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SettingsControllerTest {

    @Test
    void upCyclesSelectorFrom0To2() {
        Settings s = new Settings();
        SettingsController c = new SettingsController(s);

        assertEquals(0, s.getSelector());
        c.step(null, GUI.ACTION.UP);

        assertEquals(2, s.getSelector());  // wrap correctly
    }

    @Test
    void downMovesSelectorTo1Then2() {
        Settings s = new Settings();
        SettingsController c = new SettingsController(s);

        c.step(null, GUI.ACTION.DOWN);
        assertEquals(1, s.getSelector());

        c.step(null, GUI.ACTION.DOWN);
        assertEquals(2, s.getSelector());
    }

    @Test
    void leftDecreasesWidthWhenSelectorIs0() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);

        s.setSelector(0);
        c.step(null, GUI.ACTION.LEFT);

        assertEquals(9, s.getWidth());
    }

    @Test
    void rightIncreasesWidthWhenSelectorIs0() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);

        s.setSelector(0);
        c.step(null, GUI.ACTION.RIGHT);

        assertEquals(11, s.getWidth());
    }

    @Test
    void leftDecreasesHeightWhenSelectorIs1() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);

        s.setSelector(1);
        c.step(null, GUI.ACTION.LEFT);

        assertEquals(9, s.getHeight());
    }

    @Test
    void rightIncreasesHeightWhenSelectorIs1() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);

        s.setSelector(1);
        c.step(null, GUI.ACTION.RIGHT);

        assertEquals(11, s.getHeight());
    }

    @Test
    void rightSwitchesDifficultyRandomToHunt() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.RANDOM);
        SettingsController c = new SettingsController(s);

        s.setSelector(2);
        c.step(null, GUI.ACTION.RIGHT);

        assertEquals(Settings.BotDifficulty.HUNT, s.getDifficulty());
    }

    @Test
    void leftLoopsDifficultyBackwardsHuntToRandom() {
        Settings s = new Settings(10,10, Settings.BotDifficulty.HUNT);
        SettingsController c = new SettingsController(s);

        s.setSelector(2);
        c.step(null, GUI.ACTION.LEFT);

        assertEquals(Settings.BotDifficulty.RANDOM, s.getDifficulty());
    }

    @Test
    void selectReturnsToMenuState() {
        Game game = mock(Game.class);
        SettingsController controller = new SettingsController(new Settings());

        controller.step(game, GUI.ACTION.SELECT);

        verify(game).setState(isA(MenuState.class));
    }

    @Test
    void quitAlsoReturnsToMenuState() {
        Game game = mock(Game.class);
        SettingsController controller = new SettingsController(new Settings());

        controller.step(game, GUI.ACTION.QUIT);

        verify(game).setState(isA(MenuState.class));
    }
}
