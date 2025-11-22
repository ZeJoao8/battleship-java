package battleship.model.placement;

import battleship.model.Position;
import battleship.model.common.Board;

public class PlacementModel {
    private final Board board;
    private final Position cursor = new Position(0, 0);

    public PlacementModel(Board board) { this.board = board; }

    public Board getBoard() { return board; }
    public Position getCursor() { return cursor; }

    public void moveUp() { cursor.moveUp(); }
    public void moveLeft() { cursor.moveLeft(); }
    public void moveDown() { cursor.moveDown(board.getHeigth() - 1); }
    public void moveRight(){ cursor.moveRight(board.getWidth() - 1); }
}
