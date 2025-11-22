package battleship.viewer.placement;


import battleship.gui.GUI;
import battleship.model.placement.PlacementModel;
import battleship.model.common.Board;
import battleship.viewer.Viewer;

public class PlacementViewer extends Viewer<PlacementModel> {
    private final PlacementBoardViewer boardViewer;

    public PlacementViewer(PlacementModel model) {
        super(model);
        this.boardViewer = new PlacementBoardViewer(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        Board board = getModel().getBoard();
        gui.drawText(2, 1, "Posicione a sua frota (Q = sair)", "#FFFFFF");

        // boat info
        var current = board.getFleet().stream().filter(b -> b.getPositions().isEmpty()).findFirst().orElse(null);
        gui.drawText(2, 3, "A colocar: " + (current != null ? current.getName() : "Nenhum"), "#00FF9D");
        gui.drawText(2, 4, "Setas = mover | R = rodar | Enter = colocar", "#AAAAAA");

        boardViewer.draw(gui, 5, 6);
    }
}
