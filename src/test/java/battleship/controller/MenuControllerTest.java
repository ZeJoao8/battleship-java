package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.SettingsState;
import battleship.states.game.PlacementState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuControllerTest {

    private Menu menu;
    private MenuController controller;
    private Game game;

    @BeforeEach
    void setup() {
        menu = spy(new Menu());
        controller = new MenuController(menu);
        game = mock(Game.class);
        when(game.getSettings()).thenReturn(new Settings());
    }

    @Test
    void upMovesSelectionUp() throws Exception {
        controller.step(game, GUI.ACTION.UP);
        verify(menu).previous();
    }

    @Test
    void downMovesSelectionDown() throws Exception {
        controller.step(game, GUI.ACTION.DOWN);
        verify(menu).next();
    }

    @Test
    void selectPlayStartsPlacementState() throws Exception {
        doReturn(true).when(menu).isSelectedPlay();
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(argThat(s -> s instanceof PlacementState));
    }

    @Test
    void selectSettingsOpensSettingsState() throws Exception {
        doReturn(false).when(menu).isSelectedPlay();
        doReturn(true).when(menu).isSelectedSettings();
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(argThat(s -> s instanceof SettingsState));
    }

    @Test
    void selectQuitEndsGame() throws Exception {
        doReturn(false).when(menu).isSelectedPlay();
        doReturn(false).when(menu).isSelectedSettings();
        doReturn(true).when(menu).isSelectedQuit();
        controller.step(game, GUI.ACTION.SELECT);
        verify(game).setState(null);
    }

    @Test
    void selectDoesNothingIfNothingSelected() throws Exception {
        doReturn(false).when(menu).isSelectedPlay();
        doReturn(false).when(menu).isSelectedSettings();
        doReturn(false).when(menu).isSelectedQuit();
        controller.step(game, GUI.ACTION.SELECT);
        verify(game, never()).setState(any());
    }

    @Test
    void quitActionEndsGame() throws Exception {
        controller.step(game, GUI.ACTION.QUIT);
        verify(game).setState(null);
    }

    @Test
    void defaultActionDoesNothing() throws Exception {
        controller.step(game, GUI.ACTION.NONE);
        verify(game, never()).setState(any());
    }
}
