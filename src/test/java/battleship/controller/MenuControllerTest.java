package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.SettingsState;
import battleship.states.game.PlacementState;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MenuControllerTest {

    @Provide Arbitrary<GUI.ACTION> movementActions() {
        return Arbitraries.of(GUI.ACTION.UP, GUI.ACTION.DOWN);
    }

    @Property
    void menuWrapsCorrectly(@ForAll("movementActions") GUI.ACTION action) throws Exception {
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        controller.step(null, action);
        if (action == GUI.ACTION.UP)   assertTrue(menu.isSelectedQuit());
        if (action == GUI.ACTION.DOWN) assertTrue(menu.isSelectedSettings());
    }

    @Test
    void selectingPlayCreatesPlacementStateWithGameSettings() throws Exception {
        Game game = mock(Game.class);
        Settings s = new Settings();
        s.setWidth(12);
        s.setHeight(8);
        s.setDifficulty(Settings.BotDifficulty.HUNT);
        when(game.getSettings()).thenReturn(s);
        MenuController controller = new MenuController(new Menu());
        controller.step(game, GUI.ACTION.SELECT);
        ArgumentCaptor<PlacementState> captor = ArgumentCaptor.forClass(PlacementState.class);
        verify(game).setState(captor.capture());
        assertEquals(12, captor.getValue().getSettings().getWidth());
        assertEquals(8,  captor.getValue().getSettings().getHeight());
        assertEquals(Settings.BotDifficulty.HUNT, captor.getValue().getSettings().getDifficulty());
    }

    @Test
    void selectingSettingsOpensSettingsState() throws Exception {
        Game game = mock(Game.class);
        Settings shared = new Settings();
        shared.setWidth(10);
        shared.setHeight(9);
        shared.setDifficulty(Settings.BotDifficulty.RANDOM);
        when(game.getSettings()).thenReturn(shared);
        Menu menu = new Menu();
        menu.next();
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.SELECT);
        ArgumentCaptor<SettingsState> captor = ArgumentCaptor.forClass(SettingsState.class);
        verify(game).setState(captor.capture());
        SettingsState state = captor.getValue();
        assertSame(shared, state.getModel());
    }

    @Test
    void selectingQuitEndsGame() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        menu.next(); menu.next();
        new MenuController(menu).step(game, GUI.ACTION.SELECT);
        verify(game).setState(null);
    }

    @Test
    void pressingQuitEndsGameRegardlessOfSelection() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        menu.next(); menu.next();
        new MenuController(menu).step(game, GUI.ACTION.QUIT);
        verify(game).setState(null);
    }
}
