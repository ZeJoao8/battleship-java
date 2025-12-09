package battleship.controller.game;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.model.Menu;
import battleship.states.game.EndState;
import battleship.states.MenuState;

public class BattleController extends Controller<BattleModel> {

    public BattleController(BattleModel model) {
        super(model);
    }

    @Override
    public void step(Game game, GUI.ACTION action) {
        BattleModel model = getModel();
        Board enemy = model.getEnemyBoard();

        Position cursor = model.getCursor();

        switch (action) {
            case UP -> model.setCursor(cursor.moveUpBounded());
            case LEFT -> model.setCursor(cursor.moveLeftBounded());
            case DOWN -> model.setCursor(cursor.moveDownBounded(enemy.getHeigth() - 1));
            case RIGHT -> model.setCursor(cursor.moveRightBounded(enemy.getWidth() - 1));
            case SELECT -> handleSelect(game);
            case QUIT -> game.setState(new MenuState(new Menu()));
            default -> {}
        }
    }

    private void handleSelect(Game game) {
        BattleModel model = getModel();
        Position p = model.getCursor();
        if (model.alreadyShotEnemy(p)) return;
        model.shootPlayer(p);
        if (model.enemyLost()) {
            model.getStats().finish();
            model.getStats().setFinalModel(model);
            game.setState(new EndState(true, model.getStats()));
            return;
        }
        model.makeBotMove();
        if (model.playerLost()) {
            model.getStats().finish();
            model.getStats().setFinalModel(model);
            game.setState(new EndState(false, model.getStats()));
        }
    }
}
