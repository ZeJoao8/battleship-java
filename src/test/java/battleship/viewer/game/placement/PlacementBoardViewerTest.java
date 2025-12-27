package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.mockito.Mockito.*;

class PlacementBoardViewerTest {

    private GUI gui;
    private PlacementModel model;
    private Board board;
    private PlacementBoardViewer viewer;
    private Position pos;

    @BeforeEach
    void setup() {
        gui = mock(GUI.class);
        board = mock(Board.class);
        model = mock(PlacementModel.class);
        pos = new Position(0, 0);
        when(model.getBoard()).thenReturn(board);
        viewer = new PlacementBoardViewer(model);
    }

    @Test
    void drawsBoardAndNoBoatsWhenFleetEmpty() {
        when(board.getFleet()).thenReturn(List.of());
        when(model.getCurrentBoat()).thenReturn(null);
        try (MockedConstruction<BoardViewer> bv = mockConstruction(BoardViewer.class);
             MockedConstruction<BoatViewer> boatv = mockConstruction(BoatViewer.class)) {
            viewer.draw(gui, pos);
            verify(bv.constructed().get(0)).draw(gui, pos);
            assert boatv.constructed().isEmpty();
        }
    }

    @Test
    void drawsPlacedBoats() {
        Boat b = mock(Boat.class);
        when(board.getFleet()).thenReturn(List.of(b));
        when(model.getCurrentBoat()).thenReturn(null);
        try (MockedConstruction<BoatViewer> boatv = mockConstruction(BoatViewer.class)) {
            viewer.draw(gui, pos);
            verify(boatv.constructed().get(0)).draw(gui, pos);
        }
    }

    @Test
    void doesNotDrawCurrentBoatWhenAlreadyPlaced() {
        Boat b = mock(Boat.class);
        when(b.isPlaced()).thenReturn(true);
        when(board.getFleet()).thenReturn(List.of());
        when(model.getCurrentBoat()).thenReturn(b);
        try (MockedConstruction<BoatViewer> boatv = mockConstruction(BoatViewer.class)) {
            viewer.draw(gui, pos);
            assert boatv.constructed().isEmpty();
        }
    }

    @Test
    void drawsCurrentBoatAsValidPlacement() {
        Boat b = mock(Boat.class);
        when(b.isPlaced()).thenReturn(false);
        when(b.isHorizontal()).thenReturn(true);
        when(board.getFleet()).thenReturn(List.of());
        when(model.getCurrentBoat()).thenReturn(b);
        when(model.getCursor()).thenReturn(new Position(1, 1));
        when(board.canPlaceBoat(eq(b), any(), eq(true))).thenReturn(true);
        try (MockedConstruction<BoatViewer> boatv = mockConstruction(BoatViewer.class)) {
            viewer.draw(gui, pos);
            verify(boatv.constructed().get(0)).draw(gui, pos);
        }
    }

    @Test
    void drawsCurrentBoatAsInvalidPlacement() {
        Boat b = mock(Boat.class);
        when(b.isPlaced()).thenReturn(false);
        when(b.isHorizontal()).thenReturn(false);
        when(board.getFleet()).thenReturn(List.of());
        when(model.getCurrentBoat()).thenReturn(b);
        when(model.getCursor()).thenReturn(new Position(2, 2));
        when(board.canPlaceBoat(eq(b), any(), eq(false))).thenReturn(false);
        try (MockedConstruction<BoatViewer> boatv = mockConstruction(BoatViewer.class)) {
            viewer.draw(gui, pos);
            verify(boatv.constructed().get(0)).draw(gui, pos);
        }
    }
}
