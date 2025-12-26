package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.MenuState;
import battleship.states.SettingsState;
import battleship.states.State;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    @Test
    void selectSettingsOpensSettings() throws Exception {
        Menu menu = mock(Menu.class);
        when(menu.isSelectedSettings()).thenReturn(true);

        Settings settings = new Settings();
        Game game = mock(Game.class);
        when(game.getSettings()).thenReturn(settings);

        MenuController controller = new MenuController(menu);

        controller.step(game, GUI.ACTION.SELECT);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(game).setState((State<?>) captor.capture());

        assertTrue(captor.getValue() instanceof SettingsState);
    }

    @Test
    void selectQuitEndsGame() throws Exception {
        Menu menu = mock(Menu.class);
        when(menu.isSelectedQuit()).thenReturn(true);

        Game game = mock(Game.class);
        MenuController controller = new MenuController(menu);

        controller.step(game, GUI.ACTION.SELECT);

        verify(game).setState(null);
    }

    @Test
    void selectPlayStartsPlacement() throws Exception {
        Menu menu = mock(Menu.class);
        when(menu.isSelectedPlay()).thenReturn(true);

        Settings settings = new Settings();
        Game game = mock(Game.class);
        when(game.getSettings()).thenReturn(settings);

        MenuController controller = new MenuController(menu);

        controller.step(game, GUI.ACTION.SELECT);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(game).setState((State<?>) captor.capture());

        assertNotNull(captor.getValue());
        assertTrue(captor.getValue().getClass().getSimpleName().contains("Placement"));
    }

    @Test
    void quitActionEndsGame() throws Exception {
        Menu menu = new Menu();
        Game game = mock(Game.class);

        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.QUIT);

        verify(game).setState(null);
    }
}
