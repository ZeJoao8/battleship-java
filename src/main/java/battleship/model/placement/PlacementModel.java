package battleship.model.placement;

import battleship.model.common.Board;

public class PlacementModel {
    private final Board board;
    private int cursorX = 0;
    private int cursorY = 0;

    public PlacementModel(Board board) { this.board = board; }

    public Board getBoard() { return board; }
    public int getCursorX() { return cursorX; }
    public int getCursorY() { return cursorY; }

    public void moveUp() { if (cursorY > 0) cursorY--; }
    public void moveDown() { if (cursorY < board.getHeigth() - 1) cursorY++; }
    public void moveLeft() { if (cursorX > 0) cursorX--; }
    public void moveRight() { if (cursorX < board.getWidth() - 1) cursorX++; }

}
