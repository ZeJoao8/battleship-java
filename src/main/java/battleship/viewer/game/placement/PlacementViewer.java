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
        gui.drawText(p, "Posicione a sua frota (Q = sair)", "#FFFFFF");
        Boat current = getModel().getCurrentBoat();
        gui.drawText(new Position(p.getX(),p.getY()+2), "A colocar: " + (current != null ? current.getName() : "Nenhum"), "#00FF9D");
        gui.drawText(new Position(p.getX(),p.getY()+3), "Setas = mover | R = rodar | Enter = colocar", "#AAAAAA");
        boardViewer.draw(gui, new Position(5,6));
    }
}
