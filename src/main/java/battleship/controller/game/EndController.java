package battleship.controller.game;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.states.MenuState;
import battleship.states.game.EndState;

public class EndController extends Controller<Boolean> {

    private final EndState state;

    public EndController(Boolean model, EndState s) {
        super(model);
        this.state = s;
    }

    @Override
    public void step(Game game, GUI.ACTION action) {
        switch (action) {
            case SHOW_BOARD -> state.toggleBoards();
            case QUIT -> game.setState(new MenuState(new Menu()));
            default -> {}
        }
    }
}
