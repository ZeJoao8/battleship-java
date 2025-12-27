package battleship.states;

import battleship.model.Menu;
import battleship.viewer.MenuViewer;
import battleship.controller.MenuController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuStateTest {

    @Test
    void gettersReturnCorrectTypes() {
        Menu menu = new Menu();
        MenuState state = new MenuState(menu);
        assertSame(menu, state.getModel());
        assertTrue(state.getViewer() instanceof MenuViewer);
        assertTrue(state.getController() instanceof MenuController);
    }
}
