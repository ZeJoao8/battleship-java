package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.common.Board;
import battleship.model.common.Boat;
import battleship.model.placement.PlacementModel;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;

public class PlacementBoardViewer {

    private final PlacementModel model;

    public PlacementBoardViewer(PlacementModel model) {
        this.model = model;
    }

    public void draw(GUI gui, int offsetX, int offsetY) {

        Board board = model.getBoard();

        BoardViewer.drawBoard(gui, board, offsetX, offsetY);

        for (Boat b : board.getFleet())
            BoatViewer.drawBoatGUI(b, offsetX, offsetY, gui);

        Boat current = null;
        for (Boat b : board.getFleet())
            if (b.getPositions().isEmpty()) { current = b; break; }

        if (current != null) {

            Position cursor = model.getCursor();
            boolean valid = board.canPlaceBoat(current, cursor, current.isHorizontal());

            BoatViewer.drawPreviewGUI(current, cursor, offsetX, offsetY, gui, valid);
        }
    }
}
