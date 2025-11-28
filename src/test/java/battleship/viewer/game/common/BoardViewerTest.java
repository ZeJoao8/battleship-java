package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.CellState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BoardViewerTest {

    private GUI gui;
    private Board board;
    private BoardViewer viewer;

    @BeforeEach
    void setup() {
        gui = Mockito.mock(GUI.class);
        board = Mockito.mock(Board.class);

        Mockito.when(board.getWidth()).thenReturn(6);
        Mockito.when(board.getHeigth()).thenReturn(6);

        Mockito.when(board.getState(Mockito.any())).thenReturn(CellState.WATER);

        viewer = new BoardViewer(board);
    }

    @Test
    void drawAllCells() {
        viewer.draw(gui,new Position(0,0));

        Mockito.verify(gui, Mockito.times(36))
                .drawCharacter(Mockito.any(), Mockito.anyChar(), Mockito.anyString());
    }

    @Test
    void correctSymbols() {
        Mockito.when(board.getState(new Position(0,0))).thenReturn(CellState.HIT);
        Mockito.when(board.getState(new Position(1,0))).thenReturn(CellState.MISS);
        Mockito.when(board.getState(new Position(2,0))).thenReturn(CellState.WATER);
        viewer.draw(gui,new Position(0,0));
        Mockito.verify(gui).drawCharacter(new Position(0,0),'X',"#FF4444");
        Mockito.verify(gui).drawCharacter(new Position(2,0),'O',"#C8C8C8");
        Mockito.verify(gui).drawCharacter(new Position(4,0),'~',"#4DA6FF");
    }
}
