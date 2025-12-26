package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.CellState;
import battleship.viewer.game.battle.BattleViewer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class BattleViewerTest {

    private void setupBoard(Board board) {
        when(board.getWidth()).thenReturn(5);
        when(board.getHeight()).thenReturn(5);
        when(board.getFleet()).thenReturn(List.of());
        when(board.getState(any())).thenReturn(CellState.WATER);
    }

    @Test
    void drawsUIBoardsAndCursor() {
        GUI gui = mock(GUI.class);
        BattleModel model = mock(BattleModel.class);
        Board player = mock(Board.class);
        Board enemy  = mock(Board.class);
        setupBoard(player);
        setupBoard(enemy);
        when(model.getPlayerBoard()).thenReturn(player);
        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(new Position(2,1));
        BattleViewer viewer = new BattleViewer(model);
        viewer.drawElements(gui);
        verify(gui).drawText(
                new Position(2,1), "Batalha Naval - Q = voltar ao menu | Enter = disparar", "#FFFFFF");
        verify(gui, atLeast(10)).drawCharacter(any(), anyChar(), anyString());
        verify(gui).drawCharacter(new Position(2 + player.getWidth()*2 + 6 + 2*2, 4 + 1), '●', "#00FF9D");
    }

    @Test
    void drawsPlayerBoatsAlways() {
        GUI gui = mock(GUI.class);
        BattleModel model = mock(BattleModel.class);
        Board player = mock(Board.class);
        Board enemy  = mock(Board.class);
        Boat b1 = mock(Boat.class);
        Boat b2 = mock(Boat.class);
        setupBoard(player);
        setupBoard(enemy);
        when(player.getFleet()).thenReturn(List.of(b1,b2));
        when(model.getPlayerBoard()).thenReturn(player);
        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(model.shouldRevealBoat(any())).thenReturn(false);
        BattleViewer viewer = new BattleViewer(model);
        viewer.drawElements(gui);
        verify(b1, atLeastOnce()).getSize();
        verify(b2, atLeastOnce()).getSize();
    }

    @Test
    void drawsEnemyBoatsOnlyWhenRevealed() {
        GUI gui = mock(GUI.class);
        BattleModel model = mock(BattleModel.class);
        Board player = mock(Board.class);
        Board enemy  = mock(Board.class);
        Boat enemyBoat = mock(Boat.class);
        setupBoard(player);
        setupBoard(enemy);
        when(enemy.getFleet()).thenReturn(List.of(enemyBoat));
        when(model.getPlayerBoard()).thenReturn(player);
        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(new Position(0,0));
        BattleViewer viewer = new BattleViewer(model);
        when(model.shouldRevealBoat(enemyBoat)).thenReturn(false);
        viewer.drawElements(gui);
        verify(enemyBoat, never()).getSize();
        when(model.shouldRevealBoat(enemyBoat)).thenReturn(true);
        viewer.drawElements(gui);
        verify(enemyBoat, atLeastOnce()).getSize();
    }
}
