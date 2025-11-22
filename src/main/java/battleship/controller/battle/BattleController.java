package battleship.controller.battle;

import battleship.Game;

import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.battle.BattleModel;
import battleship.model.common.Board;
import battleship.model.menu.Menu;
import battleship.states.MenuState;
import battleship.states.EndState;

public class BattleController extends Controller<BattleModel> {

    private int cursorX = 0;
    private int cursorY = 0;

    public BattleController(BattleModel model) { super(model); }

    @Override
    public void step(Game game, GUI.ACTION action) throws Exception {
        Board enemy = getModel().getEnemyBoard();

        switch (action) {
            case UP -> { if (cursorY > 0) cursorY--; }
            case DOWN -> { if (cursorY < enemy.getHeigth() - 1) cursorY++; }
            case LEFT -> { if (cursorX > 0) cursorX--; }
            case RIGHT -> { if (cursorX < enemy.getWidth() - 1) cursorX++; }
            case SELECT -> handleSelect(game);
            case QUIT -> game.setState(new MenuState(new Menu()));
            default -> {}
        }

        // update view cursor for viewer
        getModel().setViewCursor(cursorX, cursorY);
    }

    private void handleSelect(Game game) {
        BattleModel model = getModel();
        if (model.isAlreadyShotEnemy(cursorX, cursorY)) return;

        model.playerShoot(cursorX, cursorY);
        if (model.hasEnemyLost()) {
            game.setState(new EndState(true));
            return;
        }

        // bot turn
        model.botTurn();
        if (model.hasPlayerLost()) {
            game.setState(new EndState(false));
        }
    }
}
