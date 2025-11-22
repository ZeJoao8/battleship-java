package battleship.viewer.placement;

import battleship.gui.GUI;
import battleship.model.common.Board;
import battleship.model.common.Boat;
import battleship.model.placement.PlacementModel;
import battleship.viewer.common.BoardViewer;
import battleship.viewer.common.BoatViewer;

public class PlacementBoardViewer {

    private final PlacementModel model;

    public PlacementBoardViewer(PlacementModel model) {
        this.model = model;
    }

    public void draw(GUI gui, int offsetX, int offsetY) {

        Board board = model.getBoard();

        BoardViewer.drawBoard(gui, board, offsetX, offsetY);

        for (Boat b : board.getFleet()) {
            BoatViewer.drawBoatGUI(b, offsetX, offsetY, gui);
        }

        Boat current = null;
        for (Boat b : board.getFleet()) {
            if (b.getPositions().isEmpty()) {
                current = b;
                break;
            }
        }

        if (current != null) {
            boolean valid = board.canPlaceBoat(current, model.getCursorX(), model.getCursorY(), current.isHorizontal());
            BoatViewer.drawPreviewGUI(current, model.getCursorX(), model.getCursorY(), offsetX, offsetY, gui, valid);
        }
    }
}
