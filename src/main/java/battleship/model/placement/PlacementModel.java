package battleship.model.placement;

import battleship.model.Position;
import battleship.model.common.Board;
import battleship.model.common.Boat;

public class PlacementModel {

    private final Board board;
    private Position cursor = new Position(0, 0);

    public PlacementModel(Board board) {
        this.board = board;
    }

    public Board getBoard() {return board;}
    public Position getCursor() {return cursor;}

    public void moveUp()    { cursor = cursor.moveUpBounded(); }
    public void moveLeft()  { cursor = cursor.moveLeftBounded(); }
    public void moveDown()  { cursor = cursor.moveDownBounded(board.getHeigth() - 1); }
    public void moveRight() { cursor = cursor.moveRightBounded(board.getWidth() - 1); }

    public Boat getCurrentBoat() {
        return board.getNextUnplacedBoat();
    }

    public boolean placeCurrentBoat() {
        Boat current = getCurrentBoat();
        if (current == null) return false;

        if (board.canPlaceBoat(current, cursor, current.isHorizontal())) {
            board.placeBoat(current, cursor, current.isHorizontal());
            return true;
        }
        return false;
    }

    public boolean allBoatsPlaced() {
        return board.allBoatsPlaced();
    }

    public void rotateCurrentBoat() {
        Boat current = getCurrentBoat();
        if (current == null) return;

        current.rotate();
    }
}
