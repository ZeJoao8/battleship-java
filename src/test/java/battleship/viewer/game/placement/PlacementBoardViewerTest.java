package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.viewer.game.common.BoatViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.util.List;

class PlacementBoardViewerTest {

    private PlacementModel model;
    private GUI gui;
    private Board board;
    private PlacementBoardViewer viewer;

    @BeforeEach
    void setup() {
        gui = mock(GUI.class);
        model = mock(PlacementModel.class);
        board = mock(Board.class);
        when(model.getBoard()).thenReturn(board);
        viewer = new PlacementBoardViewer(model);
    }

    @Test
    void drawsPlacedFleet() {
        Boat b1 = mock(Boat.class);
        Boat b2 = mock(Boat.class);
        when(board.getFleet()).thenReturn(List.of(b1,b2));
        when(b1.isPlaced()).thenReturn(true);
        when(b2.isPlaced()).thenReturn(true);
        viewer.draw(gui,new Position(0,0));
        verify(board).getFleet();
        verify(b1, atLeastOnce()).getSize();
        verify(b2, atLeastOnce()).getSize();
    }

    @Test
    void drawsValidPreview() {
        Boat cur = mock(Boat.class);
        when(model.getCurrentBoat()).thenReturn(cur);
        when(cur.isPlaced()).thenReturn(false);
        when(model.getCursor()).thenReturn(new Position(3,2));
        when(board.canPlaceBoat(cur,new Position(3,2),cur.isHorizontal())).thenReturn(true);
        viewer.draw(gui,new Position(0,0));
        verify(cur, atLeastOnce()).getSize();
    }

    @Test
    void drawsInvalidPreview() {
        Boat cur = mock(Boat.class);
        when(model.getCurrentBoat()).thenReturn(cur);
        when(cur.isPlaced()).thenReturn(false);
        when(model.getCursor()).thenReturn(new Position(1,1));
        when(board.canPlaceBoat(cur,new Position(1,1),cur.isHorizontal())).thenReturn(false);
        viewer.draw(gui,new Position(0,0));
        verify(cur, atLeastOnce()).getSize();
        verify(board).canPlaceBoat(cur,new Position(1,1),cur.isHorizontal());
    }

    @Test
    void doesNotDrawPreviewIfCurrentNull() {
        when(model.getCurrentBoat()).thenReturn(null);
        viewer.draw(gui,new Position(0,0));
        verify(model).getCurrentBoat();
        verifyNoMoreInteractions(gui);
    }

    @Test
    void doesNotPreviewIfBoatAlreadyPlaced() {
        Boat cur = mock(Boat.class);
        when(model.getCurrentBoat()).thenReturn(cur);
        when(cur.isPlaced()).thenReturn(true);
        viewer.draw(gui,new Position(0,0));
        verify(cur).isPlaced();
        verifyNoMoreInteractions(gui);
    }
}
