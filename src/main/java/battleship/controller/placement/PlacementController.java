package battleship.controller.placement;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.battle.BattleModel;
import battleship.model.common.Board;
import battleship.model.common.Boat;
import battleship.model.menu.Menu;
import battleship.model.placement.PlacementModel;
import battleship.model.settings.Settings;
import battleship.states.BattleState;
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

        game.setState(new BattleState(new BattleModel(playerBoard, enemy)));
    }
}
