package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.MenuState;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SettingsControllerTest {

    private Settings settings;
    private SettingsController controller;
    private Game game;

    @BeforeEach
    void setup() {
        settings = new Settings();
        controller = new SettingsController(settings);
        game = mock(Game.class);
    }

    @Test
    void upMovesSelectorUp() {
        settings.setSelector(1);
        controller.step(game, GUI.ACTION.UP);
        assertEquals(0, settings.getSelector());
    }

    @Test
    void downMovesSelectorDown() {
        settings.setSelector(0);
        controller.step(game, GUI.ACTION.DOWN);
        assertEquals(1, settings.getSelector());
    }

    @Test
    void leftDecreasesWidthWhenSelected() {
        settings.setSelector(0);
        settings.setWidth(10);
        controller.step(game, GUI.ACTION.LEFT);
        assertEquals(9, settings.getWidth());
    }

    @Test
    void leftDoesNotDecreaseWidthBelowMinimum() {
        settings.setSelector(0);
        settings.setWidth(6);
        controller.step(game, GUI.ACTION.LEFT);
        assertEquals(6, settings.getWidth());
    }

    @Test
    void leftDecreasesHeightWhenSelected() {
        settings.setSelector(1);
        settings.setHeight(10);
        controller.step(game, GUI.ACTION.LEFT);
        assertEquals(9, settings.getHeight());
    }

    @Test
    void leftDoesNotDecreaseHeightBelowMinimum() {
        settings.setSelector(1);
        settings.setHeight(6);
        controller.step(game, GUI.ACTION.LEFT);
        assertEquals(6, settings.getHeight());
    }

    @Test
    void leftCyclesDifficultyBackward() {
        settings.setSelector(2);
        settings.setDifficulty(Settings.BotDifficulty.values()[0]);
        controller.step(game, GUI.ACTION.LEFT);
        assertNotEquals(Settings.BotDifficulty.values()[0], settings.getDifficulty());
    }

    @Test
    void rightIncreasesWidthWhenSelected() {
        settings.setSelector(0);
        settings.setWidth(10);
        controller.step(game, GUI.ACTION.RIGHT);
        assertEquals(11, settings.getWidth());
    }

    @Test
    void rightDoesNotIncreaseWidthAboveMaximum() {
        settings.setSelector(0);
        settings.setWidth(14);
        controller.step(game, GUI.ACTION.RIGHT);
        assertEquals(14, settings.getWidth());
    }

    @Test
    void rightIncreasesHeightWhenSelected() {
        settings.setSelector(1);
        settings.setHeight(10);
        controller.step(game, GUI.ACTION.RIGHT);
        assertEquals(11, settings.getHeight());
    }

    @Test
    void rightDoesNotIncreaseHeightAboveMaximum() {
        settings.setSelector(1);
        settings.setHeight(14);
        controller.step(game, GUI.ACTION.RIGHT);
        assertEquals(14, settings.getHeight());
    }

    @Test
    void rightCyclesDifficultyForward() {
        settings.setSelector(2);
        Settings.BotDifficulty initial = settings.getDifficulty();
        controller.step(game, GUI.ACTION.RIGHT);
        assertNotEquals(initial, settings.getDifficulty());
    }

    @Test
    void quitReturnsToMenu() {
        controller.step(game, GUI.ACTION.QUIT);
        verify(game).setState(any(MenuState.class));
    }

    @Test
    void selectReturnsToMenu() {
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(any(MenuState.class));
    }

    private SettingsController controller(Settings s) {
        return new SettingsController(s);
    }

    @Property
    void anyActionNeverThrows(@ForAll GUI.ACTION a) {
        Settings s = new Settings();
        Game g = mock(Game.class);
        assertDoesNotThrow(() -> controller(s).step(g,a));
    }
}
