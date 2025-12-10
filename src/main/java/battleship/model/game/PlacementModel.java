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
    private boolean autoPlaced = false;

    public boolean wasAutoPlaced() { return autoPlaced; }

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
        autoPlaced = false;
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
        autoPlaced = true;
        for (Boat b : board.getFleet()) {
            for (Position pos : b.getPositions()) {
                board.markWater(pos);
            }
            b.clearPositions();
        }
        board.placeFleetRandomly();
    }

    public void undoLastBoat() {
        if (autoPlaced) {
            autoPlaced = false;
            for (Boat b : board.getFleet()) {
                for (Position p : b.getPositions()) board.markWater(p);
                b.setHorizontal(true);
                b.clearPositions();
            }
            adjustCursorBounds();
            return;
        }
        Boat last = null;
        Boat current = getCurrentBoat();
        if(current!=null)current.setHorizontal(true);
        for (int i = board.getFleet().size() - 1; i >= 0; i--) {
            Boat b = board.getFleet().get(i);
            if (b.isPlaced()) {
                last = b;
                break;
            }
        }
        if (last == null) return;
        for (Position p : last.getPositions()) {
            board.markWater(p);
        }
        last.setHorizontal(true);
        last.clearPositions();
        adjustCursorBounds();
    }

}
