package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;
import battleship.model.game.common.boats.Destroyer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BoatViewerTest {

    @Test
    void drawsPlacedHorizontalBoat() {
        GUI gui = mock(GUI.class);
        Boat b = new Destroyer();
        b.setHorizontal(true);
        b.addPosition(new Position(1,1));
        b.addPosition(new Position(2,1));
        new BoatViewer(b).draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(2,1),'<' ,"#C17F42");
        verify(gui).drawCharacter(new Position(4,1),'>' ,"#C17F42");
    }

    @Test
    void drawsPlacedVerticalBoat() {
        GUI gui = mock(GUI.class);
        Boat b = new Destroyer();
        b.setHorizontal(false);
        b.addPosition(new Position(1,1));
        b.addPosition(new Position(1,2));
        new BoatViewer(b).draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(2,1),'^',"#C17F42");
        verify(gui).drawCharacter(new Position(2,2),'v',"#C17F42");
    }

    @Test
    void drawsPreviewBoatValidAndInvalid() {
        GUI gui = mock(GUI.class);
        Boat b = new Destroyer();
        new BoatViewer(b,new Position(0,0),true).draw(gui,new Position(0,0));
        verify(gui,atLeastOnce()).drawCharacter(any(),anyChar(),eq("#00FF9D"));
        GUI gui2 = mock(GUI.class);
        new BoatViewer(b,new Position(0,0),false).draw(gui2,new Position(0,0));
        verify(gui2,atLeastOnce()).drawCharacter(any(),anyChar(),eq("#FF4444"));
    }

    @Test
    void drawsHitAndSunkBoatInRed() {
        GUI gui = mock(GUI.class);
        Boat b = new Destroyer();
        b.addPosition(new Position(0,0));
        b.addPosition(new Position(1,0));
        b.registerHit(new Position(0,0));
        b.registerHit(new Position(1,0));
        new BoatViewer(b).draw(gui,new Position(0,0));
        verify(gui,atLeastOnce()).drawCharacter(any(),anyChar(),eq("#FF4444"));
    }
}
