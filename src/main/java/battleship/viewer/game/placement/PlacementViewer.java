package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;
import battleship.viewer.Viewer;
import battleship.viewer.ViewerUtils;

public class PlacementViewer extends Viewer<battleship.model.game.PlacementModel> {

    private final PlacementBoardViewer boardViewer;

    public PlacementViewer(battleship.model.game.PlacementModel model) {
        super(model);
        this.boardViewer = new PlacementBoardViewer(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        drawTitle(gui);
        drawCurrentBoatInfo(gui);
        drawInstructions(gui);
        drawBoard(gui);
        drawReadyMessage(gui);
    }

    private void drawTitle(GUI gui) {
        String[] t = {
                "▗▄▄▖ ▗▖    ▗▄▖  ▗▄▄▖▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖",
                "▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌   ▐▌   ▐▛▚▞▜▌▐▌   ▐▛▚▖▐▌  █  ",
                "▐▛▀▘ ▐▌   ▐▛▀▜▌▐▌   ▐▛▀▀▘▐▌  ▐▌▐▛▀▀▘▐▌ ▝▜▌  █  ",
                "▐▌   ▐▙▄▄▖▐▌ ▐▌▝▚▄▄▖▐▙▄▄▖▐▌  ▐▌▐▙▄▄▖▐▌  ▐▌  █  "
        };
        for (int i = 0; i < t.length; i++)
            gui.drawText(new Position(ViewerUtils.centerX(gui, t[i]), 6 + i), t[i], "#FFFFFF");
    }

    private void drawCurrentBoatInfo(GUI gui) {
        Boat current = getModel().getCurrentBoat();
        String text = "To place: " + (current != null ? current.getName() : "None");
        gui.drawText(new Position(ViewerUtils.centerX(gui, text), ViewerUtils.centerY(gui, getModel().getBoard().getHeigth()) - 2), text, "#00FF9D");
    }

    private void drawInstructions(GUI gui) {
        String s = "ARROWS = Move | R = Rotate | ENTER = Place | A = Place randomly | U = Undo | Q = return";
        gui.drawText(new Position(ViewerUtils.centerX(gui, s), ViewerUtils.centerY(gui, getModel().getBoard().getHeigth())+getModel().getBoard().getHeigth()+2), s, "#AAAAAA");
    }

    private void drawBoard(GUI gui) {
        int x = (gui.getWidth() - getModel().getBoard().getWidth() * 2) / 2;
        int y = ViewerUtils.centerY(gui, getModel().getBoard().getHeigth());
        boardViewer.draw(gui, new Position(x, y));
    }

    private void drawReadyMessage(GUI gui) {
        if (!getModel().allBoatsPlaced()) return;
        String s = "All boats placed! Press ENTER to start.";
        gui.drawText(new Position(ViewerUtils.centerX(gui, s), ViewerUtils.centerY(gui, getModel().getBoard().getHeigth())+getModel().getBoard().getHeigth()+4), s, "#FFFFFF");
    }
}
