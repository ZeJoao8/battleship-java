package battleship.model.placement;

import battleship.model.Position;
import battleship.model.common.Board;

public class PlacementModel {
    private final Board board;
    private Position cursor = new Position(0, 0);

    public PlacementModel(Board board) {
        this.board = board;
    }

    public Board getBoard() { return board; }
    public Position getCursor() { return cursor; }
    public void setCursor(Position cursor) { this.cursor = cursor; }

    public void moveUp() {
        cursor = cursor.moveUpBounded();
    }

    public void moveLeft() {
        cursor = cursor.moveLeftBounded();
    }

    public void moveDown() {
        cursor = cursor.moveDownBounded(board.getHeigth() - 1);
    }

    public void moveRight() {
        cursor = cursor.moveRightBounded(board.getWidth() - 1);
    }
}
