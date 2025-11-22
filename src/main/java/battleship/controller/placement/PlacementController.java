package battleship.controller.placement;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.model.common.Board;
import battleship.model.common.Boat;
import battleship.model.placement.PlacementModel;
import battleship.model.settings.Settings;
import battleship.model.battle.BattleModel;
import battleship.states.BattleState;

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

        // próximo barco a colocar
        Boat current = null;
        for (Boat b : board.getFleet()) {
            if (b.getPositions().isEmpty()) {
                current = b;
                break;
            }
        }

        // se já não há barco -> iniciar batalha
        if (current == null) {
            startBattle(game, board);
            return;
        }

        switch (action) {
            case UP -> {
                model.moveUp();
                adjustCursorBounds(current, model, board);
            }
            case DOWN -> {
                model.moveDown();
                adjustCursorBounds(current, model, board);
            }
            case LEFT -> {
                model.moveLeft();
                adjustCursorBounds(current, model, board);
            }
            case RIGHT -> {
                model.moveRight();
                adjustCursorBounds(current, model, board);
            }

            case ROTATE -> {
                current.rotate();
                adjustCursorBounds(current, model, board);
            }

            case SELECT -> {
                int x = model.getCursorX();
                int y = model.getCursorY();

                if (board.canPlaceBoat(current, x, y, current.isHorizontal())) {
                    board.placeBoat(current, x, y, current.isHorizontal());
                }
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

            case QUIT -> game.setState(null);
            default -> {}
        }

    }

    private void adjustCursorBounds(Boat current, PlacementModel model, Board board) {
        if (current == null) return;

        int size = current.getSize();
        int x = model.getCursorX();
        int y = model.getCursorY();

        if (current.isHorizontal()) {
            while (x + size > board.getWidth()) {
                model.moveLeft();
                x = model.getCursorX();
            }
        } else {
            while (y + size > board.getHeigth()) {
                model.moveUp();
                y = model.getCursorY();
            }
        }
    }



    private void startBattle(Game game, Board playerBoard) {

        // usamos as settings passadas (vêm de Game e não são null)
        Settings s = (settings != null) ? settings : game.getSettings();

        Board enemy = new Board(s);
        enemy.placeFleetRandomly();

        BattleModel battleModel = new BattleModel(playerBoard, enemy);
        game.setState(new BattleState(battleModel));
    }
}
