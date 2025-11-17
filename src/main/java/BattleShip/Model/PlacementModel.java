package BattleShip.Model;

public class PlacementModel {
    private final Board board;

    private int cursorX = 0;
    private int cursorY = 0;

    public PlacementModel(Board board) {
        this.board = board;
    }
    public Board getBoard() { return board; }
    public int getCursorX() { return cursorX; }
    public int getCursorY() { return cursorY; }


    public void moveUp() { cursorY--; }
    public void moveDown() { cursorY++; }
    public void moveLeft() { cursorX--; }
    public void moveRight() { cursorX++; }

}


