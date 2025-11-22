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
        Board board = model.getBoard();

        Boat current = null;
        for (Boat b : board.getFleet()) {
            if (b.getPositions().isEmpty()) {
                current = b;
                adjustCursorBounds(current, model, board);
                break;
            }
        }

        if (current == null) {
            startBattle(game, board);
            return;
        }
        Position cursor = model.getCursor();
        switch (action) {
            case UP -> {
                model.setCursor(cursor.moveUpBounded());
                adjustCursorBounds(current, model, board);
            }
            case LEFT -> {
                model.setCursor(cursor.moveLeftBounded());
                adjustCursorBounds(current, model, board);
            }
            case DOWN -> {
                model.setCursor(cursor.moveDownBounded(model.getBoard().getHeigth() - 1));
                adjustCursorBounds(current, model, board);
            }
            case RIGHT -> {
                model.setCursor(cursor.moveRightBounded(model.getBoard().getWidth() - 1));
                adjustCursorBounds(current, model, board);
            }

            case ROTATE -> {
                current.rotate();
                adjustCursorBounds(current, model, board);
            }

            case SELECT -> {
                Position p = model.getCursor();
                if (board.canPlaceBoat(current, p, current.isHorizontal()))
                    board.placeBoat(current, p, current.isHorizontal());

                boolean allPlaced = true;
                for (Boat b : board.getFleet()) {
                    if (b.getPositions().isEmpty()) {
                        allPlaced = false;
                        break;
                    }
                }
                if (allPlaced) {
                    startBattle(game, board);
                }
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
