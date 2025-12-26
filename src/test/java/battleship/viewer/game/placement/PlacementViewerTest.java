package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.CellState;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

class PlacementViewerTest {

    private void fullyMockModel(PlacementModel model) {
        Board board = mock(Board.class);
        when(board.getWidth()).thenReturn(5);
        when(board.getHeight()).thenReturn(5);
        when(board.getFleet()).thenReturn(Collections.emptyList());
        when(board.getState(any())).thenReturn(CellState.WATER);
        when(model.getBoard()).thenReturn(board);
        Boat boat = mock(Boat.class);
        when(boat.getName()).thenReturn("Submarino");
        when(boat.isPlaced()).thenReturn(true);
        when(boat.getSize()).thenReturn(1);
        when(boat.isHorizontal()).thenReturn(true);
        when(boat.isPartHit(0)).thenReturn(false);
        when(boat.getPositions()).thenReturn(Collections.singletonList(new Position(0,0)));
        when(model.getCurrentBoat()).thenReturn(boat);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(board.canPlaceBoat(any(), any(), anyBoolean())).thenReturn(true);
    }

    @Test
    void drawsUIElementsAndBoard() {
        GUI gui = mock(GUI.class);
        PlacementModel model = mock(PlacementModel.class);
        fullyMockModel(model);
        PlacementViewer viewer = new PlacementViewer(model);
        viewer.drawElements(gui);
        verify(gui).drawText(new Position(2,1),
                "Posicione a sua frota (Q = sair)", "#FFFFFF");
        verify(gui).drawText(new Position(2,3),
                "A colocar: Submarino", "#00FF9D");
        verify(gui).drawText(new Position(2,4),
                "Setas = mover | R = rodar | Enter = colocar", "#AAAAAA");
        verify(gui, atLeast(1)).drawCharacter(any(), anyChar(), anyString());
    }

    @Test
    void boardViewerAlwaysDrawsBoard() {
        GUI gui = mock(GUI.class);
        PlacementModel model = mock(PlacementModel.class);
        fullyMockModel(model);
        PlacementViewer viewer = new PlacementViewer(model);
        viewer.drawElements(gui);
        verify(gui, atLeastOnce()).drawCharacter(any(), anyChar(), anyString());
    }

    @Test
    void showsNenhumWhenNoCurrentBoat() {
        GUI gui = mock(GUI.class);
        PlacementModel model = mock(PlacementModel.class);
        fullyMockModel(model);
        when(model.getCurrentBoat()).thenReturn(null);
        PlacementViewer viewer = new PlacementViewer(model);
        viewer.drawElements(gui);
        verify(gui).drawText(new Position(2,3),
                "A colocar: Nenhum", "#00FF9D");
    }
}
