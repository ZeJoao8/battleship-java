package battleship.viewer.game.placement;

import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.game.PlacementModel;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PlacementViewerTest {

    @Test
    void drawsPlacementScreenNormally() throws Exception {
        GUI gui = mock(GUI.class);
        PlacementModel model = new PlacementModel(new Board(new Settings()));

        PlacementViewer viewer = new PlacementViewer(model);
        viewer.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
    }

    @Test
    void showsReadyMessageWhenAllBoatsPlaced() throws Exception {
        GUI gui = mock(GUI.class);
        PlacementModel model = new PlacementModel(new Board(new Settings()));

        model.getBoard().placeFleetRandomly();

        PlacementViewer viewer = new PlacementViewer(model);
        viewer.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
    }
}
