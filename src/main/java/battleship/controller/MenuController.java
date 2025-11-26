package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.game.common.Board;
import battleship.model.Menu;
import battleship.model.game.PlacementModel;
import battleship.model.Settings;
import battleship.states.game.PlacementState;
import battleship.states.SettingsState;

public class MenuController extends Controller<Menu> {

    public MenuController(Menu menu) { super(menu); }

    @Override
    public void step(Game game, GUI.ACTION action) throws Exception {
        switch (action) {
            case UP -> getModel().previous();
            case DOWN -> getModel().next();
            case SELECT -> {
                if (getModel().isSelectedPlay()) {
                    // usa as settings partilhadas do Game
                    Settings settings = game.getSettings();
                    Board player = new Board(settings);
                    PlacementModel pm = new PlacementModel(player);
                    // passamos Settings para o PlacementState (não o enemyBoard)
                    game.setState(new PlacementState(pm, settings));
                } else if (getModel().isSelectedSettings()) {
                    // abre as settings usando as settings partilhadas
                    game.setState(new SettingsState(game.getSettings()));
                } else if (getModel().isSelectedQuit()) {
                    game.setState(null);
                }
            }
            case QUIT -> game.setState(null);
            default -> { }
        }
    }
}
