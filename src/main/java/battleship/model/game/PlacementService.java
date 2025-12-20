package battleship.model.game;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;

public class PlacementService {

    public void autoPlace(Board board) {
        clearBoard(board);
        board.placeFleetRandomly();
    }

    public void undo(Board board, boolean wasAutoPlaced) {
        if (wasAutoPlaced) {
            clearBoard(board);
            return;
        }
        Boat lastPlaced = getLastPlacedBoat(board);
        if (lastPlaced == null) return;
        for (int i = 0; i < lastPlaced.getPositions().size(); i++) {
            board.markWater(lastPlaced.getPositions().get(i));
        }
        lastPlaced.clearPositions();
        lastPlaced.setHorizontal(true);
    }

    private Boat getLastPlacedBoat(Board board) {
        for (int i = board.getFleet().size() - 1; i >= 0; i--) {
            Boat b = board.getFleet().get(i);
            if (b.isPlaced()) return b;
        }
        return null;
    }

    private void clearBoard(Board board) {
        for (int i = 0; i < board.getFleet().size(); i++) {
            Boat b = board.getFleet().get(i);
            for (int j = 0; j < b.getPositions().size(); j++) {
                board.markWater(b.getPositions().get(j));
            }
            b.clearPositions();
            b.setHorizontal(true);
        }
    }
}
