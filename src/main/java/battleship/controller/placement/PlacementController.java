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
                adjustCursorBounds(current,model,model.getBoard());
            }
            case DOWN -> {
                model.moveDown();
                adjustCursorBounds(current,model,model.getBoard());
            }
            case LEFT -> {
                model.moveLeft();
                adjustCursorBounds(current,model,model.getBoard());
            }
            case RIGHT -> {
                model.moveRight();
                adjustCursorBounds(current,model,model.getBoard());
            }

            case ROTATE -> {
                model.rotateCurrentBoat();
                adjustCursorBounds(current,model,model.getBoard());
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


    private void adjustCursorBounds(Boat current, PlacementModel model, Board board) {
        if (current == null) return;

        int size = current.getSize();
        Position c = model.getCursor();
        int x = c.getX();
        int y = c.getY();

        if (current.isHorizontal()) {
            while (x + size > board.getWidth()) {
                model.moveLeft();
                x = model.getCursor().getX();
            }
        } else {
            while (y + size > board.getHeigth()) {
                model.moveUp();
                y = model.getCursor().getY();
            }
        }
    }



    private void startBattle(Game game, Board playerBoard) {

        Settings s = (settings != null) ? settings : game.getSettings();

        Board enemy = new Board(s);
        enemy.placeFleetRandomly();

        game.setState(new BattleState(new BattleModel(playerBoard, enemy)));
    }
}
