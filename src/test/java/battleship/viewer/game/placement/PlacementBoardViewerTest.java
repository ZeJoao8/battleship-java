package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.boats.Destroyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class PlacementBoardViewerTest {

    private GUI gui;
    private PlacementModel model;
    private Board board;
    private PlacementBoardViewer viewer;

    @BeforeEach
    void setup() {
        gui = mock(GUI.class);
        board = mock(Board.class);
        model = mock(PlacementModel.class);
        when(model.getBoard()).thenReturn(board);
        viewer = new PlacementBoardViewer(model);
    }

    @Test
    void drawsBoardAndPlacedBoats() {
        Boat placed = new Destroyer();
        placed.addPosition(new Position(0,0));
        placed.addPosition(new Position(1,0));

        when(board.getFleet()).thenReturn(List.of(placed));
        when(model.getCurrentBoat()).thenReturn(null);

        viewer.draw(gui,new Position(0,0));

        verify(gui,atLeastOnce()).drawCharacter(any(),anyChar(),anyString());
    }

    @Test
    void drawsCurrentBoatPreviewWhenExists() {
        Boat current = new Destroyer();
        when(model.getCurrentBoat()).thenReturn(current);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(board.getFleet()).thenReturn(List.of());
        when(board.canPlaceBoat(eq(current),any(),anyBoolean())).thenReturn(true);

        viewer.draw(gui,new Position(0,0));

        verify(gui,atLeastOnce()).drawCharacter(any(),anyChar(),eq("#00FF9D"));
    }

    @Test
    void drawsInvalidPreviewInRed() {
        Boat current = new Destroyer();
        when(model.getCurrentBoat()).thenReturn(current);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(board.getFleet()).thenReturn(List.of());
        when(board.canPlaceBoat(eq(current),any(),anyBoolean())).thenReturn(false);

        viewer.draw(gui,new Position(0,0));

        verify(gui,atLeastOnce()).drawCharacter(any(),anyChar(),eq("#FF4444"));
    }
}
