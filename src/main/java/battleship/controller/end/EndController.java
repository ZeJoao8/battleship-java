package battleship.controller.end;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.menu.Menu;
import battleship.states.MenuState;

public class EndController extends Controller<Boolean> {
    public EndController(Boolean model) { super(model); }
    @Override
    public void step(Game game, GUI.ACTION action) {
        switch (action) {
            case QUIT, SELECT -> game.setState(new MenuState(new Menu()));
            default -> {}
        }
    }
}
