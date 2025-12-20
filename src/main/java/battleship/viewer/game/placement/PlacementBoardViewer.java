package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;
import battleship.model.game.PlacementModel;
import battleship.viewer.game.ElementViewer;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;

public class PlacementBoardViewer extends ElementViewer<PlacementModel> {

    public PlacementBoardViewer(PlacementModel model) {
        super(model);
    }

    @Override
    public void draw(GUI gui, Position pos) {
        drawBoard(gui, pos);
        drawPlacedBoats(gui, pos);
        drawCurrentBoat(gui, pos);
    }

    private void drawBoard(GUI gui, Position pos) {
        new BoardViewer(getModel().getBoard()).draw(gui, pos);
    }

    private void drawPlacedBoats(GUI gui, Position pos) {
        for (Boat b : getModel().getBoard().getFleet())
            new BoatViewer(b).draw(gui, pos);
    }

    private void drawCurrentBoat(GUI gui, Position pos) {
        var model = getModel();
        var boat = model.getCurrentBoat();
        if (boat == null || boat.isPlaced()) return;
        boolean valid = model.getBoard().canPlaceBoat(boat, model.getCursor(), boat.isHorizontal());
        new BoatViewer(boat, model.getCursor(), valid).draw(gui, pos);
    }
}
