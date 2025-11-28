package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class PlacementViewerTest {

    @Test
    void drawElements_withCurrentBoat_doesNotThrow() {
        GUI gui = mock(GUI.class);
        PlacementModel model = mock(PlacementModel.class);
        Board board = mock(Board.class);
        Boat boat = mock(Boat.class);

        when(model.getBoard()).thenReturn(board);
        when(board.getFleet()).thenReturn(Collections.emptyList());

        when(model.getCurrentBoat()).thenReturn(boat);
        when(boat.isPlaced()).thenReturn(true);

        PlacementViewer viewer = new PlacementViewer(model);

        assertDoesNotThrow(() -> viewer.drawElements(gui));
    }

    @Test
    void drawElements_withoutCurrentBoat_doesNotThrow() {
        GUI gui = mock(GUI.class);
        PlacementModel model = mock(PlacementModel.class);
        Board board = mock(Board.class);

        when(model.getBoard()).thenReturn(board);
        when(board.getFleet()).thenReturn(Collections.emptyList());

        when(model.getCurrentBoat()).thenReturn(null);

        PlacementViewer viewer = new PlacementViewer(model);

        assertDoesNotThrow(() -> viewer.drawElements(gui));
    }
}
