package battleship.controller.game;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.Menu;
import battleship.model.game.PlacementModel;
import battleship.model.Settings;
import battleship.states.game.BattleState;
import battleship.states.MenuState;

public class PlacementController extends Controller<PlacementModel> {

    private final Settings settings;

    public PlacementController(PlacementModel model, Settings settings) {
        super(model);
        this.settings = settings;
    }

    @Override
    public void step(Game game, GUI.ACTION action) {

        PlacementModel model = getModel();

        Boat current = model.getCurrentBoat();

        if (current == null) {
            startBattle(game, model.getBoard());
            return;
        }

        switch (action) {
            case UP -> {
                model.moveUp();
            }
            case DOWN -> {
                model.moveDown();
            }
            case LEFT -> {
                model.moveLeft();
            }
            case RIGHT -> {
                model.moveRight();
            }

            case ROTATE -> {
                model.rotateCurrentBoat();
            }

            case SELECT -> {
                model.placeCurrentBoat();
                if (model.allBoatsPlaced())
                    startBattle(game, model.getBoard());
            }

            case QUIT -> game.setState(new MenuState(new Menu()));
            default -> {}
        }
    }

    private void startBattle(Game game, Board playerBoard) {
        Settings s = (settings != null) ? settings : game.getSettings();
        Board enemy = new Board(s);
        enemy.placeFleetRandomly();
        assert settings != null;
        game.setState(new BattleState(new BattleModel(playerBoard, enemy, s)));
    }
}
