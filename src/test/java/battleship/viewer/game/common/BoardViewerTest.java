package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.CellState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BoardViewerTest {

    private GUI gui;
    private Board board;
    private BoardViewer viewer;

    @BeforeEach
    void setup() {
        gui = mock(GUI.class);
        board = mock(Board.class);
        when(board.getWidth()).thenReturn(3);
        when(board.getHeight()).thenReturn(2);
        when(board.getState(any())).thenReturn(CellState.WATER);
        viewer = new BoardViewer(board);
    }

    @Test
    void drawsAllCells() {
        viewer.draw(gui,new Position(0,0));
        verify(gui,times(6)).drawCharacter(any(),anyChar(),anyString());
    }

    @Test
    void drawsCorrectSymbolsAndColors() {
        when(board.getState(new Position(0,0))).thenReturn(CellState.HIT);
        when(board.getState(new Position(1,0))).thenReturn(CellState.MISS);
        viewer.draw(gui,new Position(0,0));
        verify(gui).drawCharacter(new Position(0,0),'X',"#FF4444");
        verify(gui).drawCharacter(new Position(2,0),'O',"#C8C8C8");
        verify(gui).drawCharacter(new Position(4,0),'~',"#4DA6FF");
    }
}
