package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.common.Board;
import battleship.model.common.Boat;
import battleship.model.placement.PlacementModel;
import battleship.viewer.game.ElementViewer;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;

public class PlacementBoardViewer extends ElementViewer<PlacementModel> {

    public PlacementBoardViewer(PlacementModel model) {
        super(model);
    }

    @Override
    public void draw(GUI gui, Position pos) {
        PlacementModel model = getModel();
        Board board = model.getBoard();
        new BoardViewer(board).draw(gui, pos);
        for (Boat b : board.getFleet()) {
            new BoatViewer(b).draw(gui, pos);
        }
        Boat current = model.getCurrentBoat();
        if (current != null && !current.isPlaced()) {
            Position cursor = model.getCursor();
            boolean valid = board.canPlaceBoat(current, cursor, current.isHorizontal());
            new BoatViewer(current, cursor, valid).draw(gui, pos);
        }
    }
}
