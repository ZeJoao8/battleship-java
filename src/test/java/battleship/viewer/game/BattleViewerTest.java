package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.CellState;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BattleViewerTest {

    @Test
    void drawElementsAndDrawsCursor() {
        GUI gui = mock(GUI.class);

        BattleModel model = mock(BattleModel.class);
        Board playerBoard = mock(Board.class);
        Board enemyBoard = mock(Board.class);

        when(model.getPlayerBoard()).thenReturn(playerBoard);
        when(model.getEnemyBoard()).thenReturn(enemyBoard);

        when(playerBoard.getWidth()).thenReturn(5);
        when(playerBoard.getHeigth()).thenReturn(5);
        when(enemyBoard.getWidth()).thenReturn(5);
        when(enemyBoard.getHeigth()).thenReturn(5);

        when(playerBoard.getFleet()).thenReturn(Collections.emptyList());
        when(enemyBoard.getFleet()).thenReturn(Collections.emptyList());

        when(playerBoard.getState(any(Position.class))).thenReturn(CellState.WATER);
        when(enemyBoard.getState(any(Position.class))).thenReturn(CellState.WATER);

        when(model.getCursor()).thenReturn(new Position(0, 0));

        when(model.shouldRevealBoat(any(Boat.class))).thenReturn(false);

        BattleViewer viewer = new BattleViewer(model);

        assertDoesNotThrow(() -> viewer.drawElements(gui));

        verify(gui, atLeastOnce())
                .drawCharacter(any(Position.class), eq('●'), eq("#00FF9D"));
    }
}
