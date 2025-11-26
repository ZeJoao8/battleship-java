package battleship.controller;

import battleship.Game;
import battleship.controller.MenuController;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.SettingsState;
import battleship.states.game.PlacementState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuControllerTest {

    @Test
    void upMovesSelectionUp() throws Exception {
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        assertTrue(menu.isSelectedPlay());
        controller.step(null, GUI.ACTION.UP);
        assertTrue(menu.isSelectedQuit());
        assertFalse(menu.isSelectedPlay());
        assertFalse(menu.isSelectedSettings());
    }

    @Test
    void downMovesSelectionDown() throws Exception {
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        assertTrue(menu.isSelectedPlay());
        controller.step(null, GUI.ACTION.DOWN);
        assertTrue(menu.isSelectedSettings());
        assertFalse(menu.isSelectedPlay());
        assertFalse(menu.isSelectedQuit());
    }

    @Test
    void startGameLeadsToPlacementState() throws Exception {
        Game game = mock(Game.class);
        when(game.getSettings()).thenReturn(new Settings());
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(isA(PlacementState.class));
    }



    @Test
    void openSettingsMenu() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        menu.next();
        assertTrue(menu.isSelectedSettings());
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(isA(SettingsState.class));
    }

    @Test
    void quitEndsGameOnSelect() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        menu.next();
        menu.next();
        assertTrue(menu.isSelectedQuit());
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(null);
    }

    @Test
    void quitEndsGameOnQuitAction() throws Exception {
        Game game = mock(Game.class);
        Menu menu = new Menu();
        MenuController controller = new MenuController(menu);
        controller.step(game, GUI.ACTION.QUIT);
        verify(game).setState(null);
    }
}
