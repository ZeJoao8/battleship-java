package battleship.model.game.common;

import battleship.model.Position;

import java.util.Random;

public class BoardPlacementService {

    public boolean canPlace(Board board, Boat boat, Position start, boolean horizontal) {
        int size = boat.getSize();
        for (int i = 0; i < size; i++) {
            Position p = horizontal ? new Position(start.getX() + i, start.getY()) : new Position(start.getX(), start.getY() + i);
            if (!board.isInside(p)) return false;
            if (!board.isWater(p)) return false;
        }
        return true;
    }

    public void place(Board board, Boat boat, Position start, boolean horizontal) {
        if (!canPlace(board, boat, start, horizontal)) return;
        for (int i = 0; i < boat.getPositions().size(); i++) {
            board.markWater(boat.getPositions().get(i));
        }
        boat.clearPositions();
        boat.setHorizontal(horizontal);
        for (int i = 0; i < boat.getSize(); i++) {
            Position p = horizontal ? new Position(start.getX() + i, start.getY()) : new Position(start.getX(), start.getY() + i);
            board.markShip(p);
            boat.addPosition(p);
        }
    }

    public void placeFleetRandomly(Board board) {
        Random rnd = new Random();
        for (int i = 0; i < board.getFleet().size(); i++) {
            Boat boat = board.getFleet().get(i);
            boolean placed = false;
            while (!placed) {
                boolean horizontal = rnd.nextBoolean();
                Position p = new Position(rnd.nextInt(board.getWidth()), rnd.nextInt(board.getHeight()));
                if (canPlace(board, boat, p, horizontal)) {
                    place(board, boat, p, horizontal);
                    placed = true;
                }
            }
        }
    }
}
