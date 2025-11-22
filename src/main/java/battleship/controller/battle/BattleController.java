package battleship.controller.battle;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.battle.BattleModel;
import battleship.model.common.Board;
import battleship.model.menu.Menu;
import battleship.states.EndState;
import battleship.states.MenuState;

public class BattleController extends Controller<BattleModel> {

    public BattleController(BattleModel model) {
        super(model);
    }

    @Override
    public void step(Game game, GUI.ACTION action) {
        BattleModel model = getModel();
        Board enemy = model.getEnemyBoard();

        Position cursor = model.getViewCursor();

        switch (action) {
            case UP    -> cursor.moveUp();
            case LEFT  -> cursor.moveLeft();
            case DOWN  -> cursor.moveDown(enemy.getHeigth() - 1);
            case RIGHT -> cursor.moveRight(enemy.getWidth() - 1);
            case SELECT -> handleSelect(game, cursor);
            case QUIT   -> game.setState(new MenuState(new Menu()));
            default -> {}
        }
    }

    private void handleSelect(Game game, Position p) {
        BattleModel model = getModel();

        if (model.isAlreadyShotEnemy(p))
            return;

        model.playerShoot(p);

        if (model.hasEnemyLost()) {
            game.setState(new EndState(true));
            return;
        }

        model.botTurn();
        if (model.hasPlayerLost()) {
            game.setState(new EndState(false));
        }
    }
}
