package battleship.model.game;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;

public class PlacementModel {

    private final Board board;
    private final PlacementService service;
    private Position cursor = new Position(0, 0);
    private boolean autoPlaced = false;

    public PlacementModel(Board board) {
        this.board = board;
        this.service = new PlacementService();
    }

    public Board getBoard() { return board; }
    public Position getCursor() { return cursor; }
    public boolean wasAutoPlaced() { return autoPlaced; }

    public void moveUp() {
        cursor = cursor.moveUpBounded();
        adjustCursorBounds();
    }

    public void moveDown() {
        cursor = cursor.moveDownBounded(board.getHeight() - 1);
        adjustCursorBounds();
    }

    public void moveLeft() {
        cursor = cursor.moveLeftBounded();
        adjustCursorBounds();
    }

    public void moveRight() {
        cursor = cursor.moveRightBounded(board.getWidth() - 1);
        adjustCursorBounds();
    }

    public Boat getCurrentBoat() {
        return board.getNextUnplacedBoat();
    }

    public void rotateCurrentBoat() {
        Boat current = getCurrentBoat();
        if (current == null) return;
        current.rotate();
        adjustCursorBounds();
    }

    public void placeCurrentBoat() {
        Boat current = getCurrentBoat();
        if (current == null) return;
        if (board.canPlaceBoat(current, cursor, current.isHorizontal())) {
            board.placeBoat(current, cursor, current.isHorizontal());
            autoPlaced = false;
            adjustCursorBounds();
        }
    }

    public void autoPlaceFleet() {
        service.autoPlace(board);
        autoPlaced = true;
        adjustCursorBounds();
    }

    public void undoLastBoat() {
        service.undo(board, autoPlaced);
        autoPlaced = false;
        adjustCursorBounds();
    }

    public boolean allBoatsPlaced() {
        return board.allBoatsPlaced();
    }

    private void adjustCursorBounds() {
        Boat current = getCurrentBoat();
        if (current == null) return;
        int size = current.getSize();
        if (current.isHorizontal()) {
            int maxX = board.getWidth() - size;
            if (cursor.getX() > maxX)
                cursor = new Position(maxX, cursor.getY());
        } else {
            int maxY = board.getHeight() - size;
            if (cursor.getY() > maxY)
                cursor = new Position(cursor.getX(), maxY);
        }
    }
}
