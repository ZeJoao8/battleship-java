package battleship.model.game;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;

public class PlacementModel {

    private final Board board;
    private Position cursor = new Position(0, 0);

    public PlacementModel(Board board) {
        this.board = board;
    }

    public Board getBoard() {return board;}
    public Position getCursor() {return cursor;}
    public void setCursor(Position p) {this.cursor = p;}

    public void moveUp()    {
        cursor = cursor.moveUpBounded();
        adjustCursorBounds();
    }
    public void moveLeft()  {
        cursor = cursor.moveLeftBounded();
        adjustCursorBounds();
    }
    public void moveDown()  {
        cursor = cursor.moveDownBounded(board.getHeigth() - 1);
        adjustCursorBounds();
    }
    public void moveRight() {
        cursor = cursor.moveRightBounded(board.getWidth() - 1);
        adjustCursorBounds();
    }

    private void adjustCursorBounds() {
        Boat current = getCurrentBoat();
        if (current == null) return;
        Position c = cursor;
        int size = current.getSize();
        if (current.isHorizontal()) {
            int maxX = board.getWidth() - size;
            if (c.getX() > maxX) {
                c = new Position(maxX, c.getY());
                cursor = c;
            }
        }
        else {
            int maxY = board.getHeigth() - size;
            if (c.getY() > maxY) {
                c = new Position(c.getX(), maxY);
                cursor = c;
            }
        }
    }

    public Boat getCurrentBoat() {
        return board.getNextUnplacedBoat();
    }

    public void placeCurrentBoat() {
        Boat current = getCurrentBoat();

        if (current == null) return;

        if (board.canPlaceBoat(current, cursor, current.isHorizontal())) {
            board.placeBoat(current, cursor, current.isHorizontal());
            adjustCursorBounds();
        }
    }

    public boolean allBoatsPlaced() {
        return board.allBoatsPlaced();
    }

    public void rotateCurrentBoat() {
        Boat current = getCurrentBoat();
        if (current == null) return;
        current.rotate();
        adjustCursorBounds();
    }

    public void autoPlaceFleet() {
        for (Boat b : board.getFleet()) {
            for (Position pos : b.getPositions()) {
                board.markWater(pos);
            }
            b.clearPositions();
        }
        board.placeFleetRandomly();
    }

    public void undoLastBoat() {
        Boat current = getCurrentBoat();
        current.setHorizontal(true);
        Boat toRemove = null;
        for (int i = board.getFleet().size() - 1; i >= 0; i--) {
            Boat b = board.getFleet().get(i);
            if (b.isPlaced()) {
                toRemove = b;
                break;
            }
        }
        if (toRemove == null) return;
        for (Position p : toRemove.getPositions()) {
            board.markWater(p);
        }
        toRemove.clearPositions();
        toRemove.setHorizontal(true);
        adjustCursorBounds();
    }
}
