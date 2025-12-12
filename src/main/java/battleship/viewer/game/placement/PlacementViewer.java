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
        Position p= new Position(2,1);
        gui.drawText(p, "Place your fleet", "#FFFFFF");
        Boat current = getModel().getCurrentBoat();
        String s1 =  "To place: " + (current != null ? current.getName() : "None");

        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),(gui.getHeight() - boardViewer.getModel().getBoard().getHeigth()) / 2 - 2), s1, "#00FF9D");
        gui.drawText(new Position(p.getX(),p.getY()+3), "Arrows = Move | R = Rotate | Enter = Place | A = Place randomly | U = Undo | Q to return", "#AAAAAA");


        int offSetX = (gui.getWidth() - boardViewer.getModel().getBoard().getWidth()*2) / 2;
        int offSetY = (gui.getHeight() - boardViewer.getModel().getBoard().getHeigth()) / 2;

        boardViewer.draw(gui, new Position(offSetX,offSetY));

        if (getModel().allBoatsPlaced()) {
            gui.drawText(
                    new Position(p.getX(), p.getY()+6+ boardViewer.getModel().getBoard().getHeigth()+1),
                    "All boats placed! Press ENTER to start.",
                    "#FFFFFF"
            );
        }
    }
}
