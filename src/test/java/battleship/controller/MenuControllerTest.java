package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.SettingsState;
import battleship.states.game.PlacementState;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MenuControllerTest {

    @Test
    void pressingUpMovesSelectionUp() throws Exception {
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        controller.step(null, GUI.ACTION.UP);
        assertTrue(menu.isSelectedQuit());
    }

    @Test
    void pressingDownMovesSelectionDown() throws Exception {
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        controller.step(null, GUI.ACTION.DOWN);
        assertTrue(menu.isSelectedSettings());
    }

    @Test
    void selectingPlayCreatesPlacementStateWithCorrectSettings() throws Exception {
        Game game = mock(Game.class);
        Settings s = new Settings();
        s.setWidth(12);
        s.setHeight(8);
        s.setDifficulty(Settings.BotDifficulty.HUNT);
        when(game.getSettings()).thenReturn(s);
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.SELECT);
        ArgumentCaptor<PlacementState> captor = ArgumentCaptor.forClass(PlacementState.class);
        verify(game).setState(captor.capture());
        PlacementState state = captor.getValue();
        assertEquals(12, state.getSettings().getWidth());
        assertEquals(8,  state.getSettings().getHeight());
        assertEquals(Settings.BotDifficulty.HUNT, state.getSettings().getDifficulty());
    }

    @Test
    void selectingSettingsOpensSettingsStateAndPassesSameSettingsInstance() throws Exception {
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
    void selectingQuitEndsTheGame() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        menu.next();
        menu.next();
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(null);
    }

    @Test
    void pressingQuitAlwaysEndsGameRegardlessOfSelection() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        menu.next(); menu.next();
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.QUIT);
        verify(game).setState(null);
    }
}
