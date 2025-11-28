package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BoatViewerTest {

    private GUI gui;
    private Boat boat;

    @BeforeEach
    void setup() {
        gui = mock(GUI.class);
        boat = mock(Boat.class);
    }

    @Test
    void placedBoatDrawsCorrectCharacters() {
        when(boat.getSize()).thenReturn(3);
        when(boat.isPlaced()).thenReturn(true);
        when(boat.isHorizontal()).thenReturn(true);
        when(boat.getPositions()).thenReturn(List.of(
                new Position(2,2),
                new Position(3,2),
                new Position(4,2)
        ));
        BoatViewer viewer = new BoatViewer(boat);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(4,2),'<',"#C17F42");
        verify(gui).drawCharacter(new Position(6,2),'-',"#C17F42");
        verify(gui).drawCharacter(new Position(8,2),'>',"#C17F42");
        verify(gui,times(3)).drawCharacter(any(),anyChar(),anyString());
    }

    @Test
    void placedBoatHitAppliesCorrectColor() {
        when(boat.getSize()).thenReturn(3);
        when(boat.isPlaced()).thenReturn(true);
        when(boat.isHorizontal()).thenReturn(true);
        when(boat.getPositions()).thenReturn(List.of(new Position(2,2),new Position(3,2),new Position(4,2)));
        when(boat.isPartHit(1)).thenReturn(true);
        BoatViewer viewer = new BoatViewer(boat);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(6,2),'-',"#FF4444");
    }

    @Test
    void placedSunkBoatDrawsAllRed() {
        when(boat.getSize()).thenReturn(2);
        when(boat.isPlaced()).thenReturn(true);
        when(boat.isHorizontal()).thenReturn(true);
        when(boat.isSunk()).thenReturn(true);
        when(boat.getPositions()).thenReturn(List.of(new Position(1,1),new Position(2,1)));

        BoatViewer viewer = new BoatViewer(boat);
        viewer.draw(gui,new Position(0,0));

        verify(gui,times(2)).drawCharacter(any(),anyChar(),eq("#FF4444"));
    }

    @Test
    void previewBoatValidIsGreen() {
        when(boat.getSize()).thenReturn(4);
        when(boat.isPlaced()).thenReturn(false);
        when(boat.isHorizontal()).thenReturn(true);
        BoatViewer viewer = new BoatViewer(boat,new Position(2,3),true);
        viewer.draw(gui,new Position(0,0));
        verify(gui,times(4)).drawCharacter(any(),anyChar(),eq("#00FF9D"));
    }

    @Test
    void previewBoatInvalidIsRed() {
        when(boat.getSize()).thenReturn(3);
        when(boat.isPlaced()).thenReturn(false);
        when(boat.isHorizontal()).thenReturn(false);
        BoatViewer viewer = new BoatViewer(boat,new Position(5,5),false);
        viewer.draw(gui,new Position(0,0));
        verify(gui,times(3)).drawCharacter(any(),anyChar(),eq("#FF4444"));
    }

    @Test
    void previewBoatNoCursorDrawsNothing() {
        when(boat.getSize()).thenReturn(3);
        when(boat.isPlaced()).thenReturn(false);
        BoatViewer viewer = new BoatViewer(boat,null,true);
        viewer.draw(gui,new Position(0,0));
        verify(gui,never()).drawCharacter(any(),anyChar(),anyString());
    }

    @Test
    void getCharHorizontal() {
        when(boat.getSize()).thenReturn(3);
        when(boat.isHorizontal()).thenReturn(true);
        BoatViewer v = new BoatViewer(boat);
        assertEquals('<', v.getChar(boat,0));
        assertEquals('-', v.getChar(boat,1));
        assertEquals('>', v.getChar(boat,2));
    }

    @Test
    void getCharVertical() {
        when(boat.getSize()).thenReturn(3);
        when(boat.isHorizontal()).thenReturn(false);
        BoatViewer v = new BoatViewer(boat);
        assertEquals('^', v.getChar(boat,0));
        assertEquals('|', v.getChar(boat,1));
        assertEquals('v', v.getChar(boat,2));
    }
}
