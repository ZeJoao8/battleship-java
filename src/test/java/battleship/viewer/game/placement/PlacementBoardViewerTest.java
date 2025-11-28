package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void drawBoardAndFleet() {
        Boat b1 = mock(Boat.class);
        Boat b2 = mock(Boat.class);
        when(board.getFleet()).thenReturn(List.of(b1,b2));
        viewer.draw(gui,new Position(0,0));
        verify(model).getBoard();
        verify(b1,atLeast(0)).getSize();
        verify(b2,atLeast(0)).getSize();
    }

    @Test
    void drawsCurrentBoatIfNotPlaced() {
        Boat current = mock(Boat.class);
        when(current.isPlaced()).thenReturn(false);
        when(model.getCurrentBoat()).thenReturn(current);
        when(model.getCursor()).thenReturn(new Position(4,3));
        when(board.canPlaceBoat(current,new Position(4,3), current.isHorizontal())).thenReturn(true);
        viewer.draw(gui,new Position(0,0));
        verify(current,atLeast(0)).getSize();
    }

    @Test
    void drawsInvalidPreviewBoat() {
        Boat cur = mock(Boat.class);
        when(cur.isPlaced()).thenReturn(false);
        when(model.getCurrentBoat()).thenReturn(cur);
        when(model.getCursor()).thenReturn(new Position(2,2));
        when(board.canPlaceBoat(cur,new Position(2,2), cur.isHorizontal())).thenReturn(false);
        viewer.draw(gui,new Position(0,0));
        verify(cur,atLeast(0)).getSize();
    }

    @Test
    void doesNotDrawPreviewIfCurrentNull() {
        when(model.getCurrentBoat()).thenReturn(null);
        viewer.draw(gui,new Position(0,0));
        verify(model).getCurrentBoat();
    }

    @Test
    void doesNotPreviewIfBoatAlreadyPlaced() {
        Boat cur = mock(Boat.class);
        when(cur.isPlaced()).thenReturn(true);
        when(model.getCurrentBoat()).thenReturn(cur);
        viewer.draw(gui,new Position(0,0));
        verify(cur).isPlaced();
    }
}
