package battleship.viewer.game.placement;


import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;
import battleship.model.game.PlacementModel;
import battleship.viewer.Viewer;

public class PlacementViewer extends Viewer<PlacementModel> {
    private final PlacementBoardViewer boardViewer;

    public PlacementViewer(PlacementModel model) {
        super(model);
        this.boardViewer = new PlacementBoardViewer(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        String s1 ="▗▄▄▖ ▗▖    ▗▄▖  ▗▄▄▖▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖";
        String s2 ="▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌   ▐▌   ▐▛▚▞▜▌▐▌   ▐▛▚▖▐▌  █  ";
        String s3 ="▐▛▀▘ ▐▌   ▐▛▀▜▌▐▌   ▐▛▀▀▘▐▌  ▐▌▐▛▀▀▘▐▌ ▝▜▌  █  ";
        String s4 ="▐▌   ▐▙▄▄▖▐▌ ▐▌▝▚▄▄▖▐▙▄▄▖▐▌  ▐▌▐▙▄▄▖▐▌  ▐▌  █  ";
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),6), s1, "#FFFFFF");
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),7), s2, "#FFFFFF");
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),8), s3, "#FFFFFF");
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),9), s4, "#FFFFFF");

        Boat current = getModel().getCurrentBoat();
        String s5 =  "To place: " + (current != null ? current.getName() : "None");
        gui.drawText(new Position(((gui.getWidth() - s5.length()) / 2),(gui.getHeight() - boardViewer.getModel().getBoard().getHeigth()) / 2 - 2), s5, "#00FF9D");
        String s6="Arrows = Move | R = Rotate | Enter = Place | A = Place randomly | U = Undo | Q = return";
        gui.drawText(new Position(((gui.getWidth() - s6.length()) / 2),(gui.getHeight() + boardViewer.getModel().getBoard().getHeigth()) / 2 + 2), s6, "#AAAAAA");


        int offSetX = (gui.getWidth() - boardViewer.getModel().getBoard().getWidth()*2) / 2;
        int offSetY = (gui.getHeight() - boardViewer.getModel().getBoard().getHeigth()) / 2;

        boardViewer.draw(gui, new Position(offSetX,offSetY));

        if (getModel().allBoatsPlaced()) {
            String s7="All boats placed! Press ENTER to start.";
            gui.drawText(
                    new Position(((gui.getWidth() - s7.length()) / 2),(gui.getHeight() + boardViewer.getModel().getBoard().getHeigth()) / 2 + 4),
                    s7,
                    "#FFFFFF"
            );
        }
    }
}
