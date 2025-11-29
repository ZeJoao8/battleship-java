package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoatViewerTest {

    @Test
    void drawsHorizontalBoatCorrectly() {
        Boat b = mock(Boat.class);
        GUI gui = mock(GUI.class);

        when(b.getSize()).thenReturn(3);
        when(b.isPlaced()).thenReturn(true);
        when(b.isHorizontal()).thenReturn(true);
        when(b.getPositions()).thenReturn(
                java.util.List.of(new Position(2,1), new Position(3,1), new Position(4,1))
        );
        BoatViewer viewer = new BoatViewer(b);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(4,1), '<', "#C17F42");
        verify(gui).drawCharacter(new Position(6,1), '-', "#C17F42");
        verify(gui).drawCharacter(new Position(8,1), '>', "#C17F42");
    }

    @Test
    void drawsVerticalBoatCorrectly() {
        Boat b = mock(Boat.class);
        GUI gui = mock(GUI.class);
        when(b.getSize()).thenReturn(3);
        when(b.isPlaced()).thenReturn(true);
        when(b.isHorizontal()).thenReturn(false);
        when(b.getPositions()).thenReturn(java.util.List.of(new Position(5,0), new Position(5,1), new Position(5,2)));
        BoatViewer viewer = new BoatViewer(b);
        viewer.draw(gui,new Position(1,1));
        verify(gui).drawCharacter(new Position(1+5*2,1+0),'^',"#C17F42");
        verify(gui).drawCharacter(new Position(1+5*2,1+1),'|',"#C17F42");
        verify(gui).drawCharacter(new Position(1+5*2,1+2),'v',"#C17F42");
    }

    @Test
    void drawsCorrectColorWhenPartHit() {
        Boat b = mock(Boat.class);
        GUI gui = mock(GUI.class);
        when(b.getSize()).thenReturn(3);
        when(b.isPlaced()).thenReturn(true);
        when(b.isHorizontal()).thenReturn(true);
        when(b.getPositions()).thenReturn(java.util.List.of(new Position(0,0),new Position(1,0),new Position(2,0)));
        when(b.isPartHit(0)).thenReturn(false);
        when(b.isPartHit(1)).thenReturn(true);
        when(b.isPartHit(2)).thenReturn(false);
        BoatViewer viewer = new BoatViewer(b);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(0,0),'<',"#C17F42");
        verify(gui).drawCharacter(new Position(2,0),'-',"#FF4444");
        verify(gui).drawCharacter(new Position(4,0),'>' ,"#C17F42");
    }

    @Test
    void drawsSunkBoatAllRed() {
        Boat b = mock(Boat.class);
        GUI gui = mock(GUI.class);
        when(b.getSize()).thenReturn(2);
        when(b.isPlaced()).thenReturn(true);
        when(b.isHorizontal()).thenReturn(true);
        when(b.isSunk()).thenReturn(true);
        when(b.getPositions()).thenReturn(java.util.List.of(new Position(4,4),new Position(5,4)));
        BoatViewer viewer = new BoatViewer(b);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(8,4),'<',"#FF4444");
        verify(gui).drawCharacter(new Position(10,4),'>' ,"#FF4444");
    }

    @Test
    void drawsPreviewBoatHorizontal() {
        Boat b = mock(Boat.class);
        GUI gui = mock(GUI.class);
        when(b.getSize()).thenReturn(3);
        when(b.isPlaced()).thenReturn(false);
        when(b.isHorizontal()).thenReturn(true);
        Position cursor = new Position(2,2);
        BoatViewer viewer = new BoatViewer(b, cursor, true);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(4,2),'<',"#00FF9D");
        verify(gui).drawCharacter(new Position(6,2),'-',"#00FF9D");
        verify(gui).drawCharacter(new Position(8,2),'>' ,"#00FF9D");
    }

    @Test
    void drawsPreviewInvalidBoatInRed() {
        Boat b = mock(Boat.class);
        GUI gui = mock(GUI.class);
        when(b.getSize()).thenReturn(2);
        when(b.isPlaced()).thenReturn(false);
        when(b.isHorizontal()).thenReturn(false);
        Position cursor = new Position(3,3);
        BoatViewer viewer = new BoatViewer(b, cursor, false);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(6,3),'^',"#FF4444");
        verify(gui).drawCharacter(new Position(6,4),'v',"#FF4444");
    }
}
