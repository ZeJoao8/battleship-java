package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.states.MenuState;
import battleship.states.game.EndState;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BattleControllerTest {

    private BattleController controller(BattleModel model) {
        return new BattleController(model);
    }

    @Test
    void cursorMovesUp() throws Exception {
        BattleModel model = mock(BattleModel.class);
        Board enemy = mock(Board.class);
        Position p = new Position(1,1);
        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(p);
        controller(model).step(mock(Game.class), GUI.ACTION.UP);
        verify(model).setCursor(p.moveUpBounded());
    }

    @Test
    void cursorMovesDownWithBounds() throws Exception {
        BattleModel model = mock(BattleModel.class);
        Board enemy = mock(Board.class);
        Position p = new Position(1,1);
        when(enemy.getHeight()).thenReturn(5);
        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(p);
        controller(model).step(mock(Game.class), GUI.ACTION.DOWN);
        verify(model).setCursor(p.moveDownBounded(4));
    }

    @Test
    void selectEndsGameOnPlayerWin() throws Exception {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(model.playTurn(any())).thenReturn(TurnResult.PLAYER_WON);
        when(model.getStats()).thenReturn(null);
        controller(model).step(game, GUI.ACTION.SELECT);
        verify(game).setState(any(EndState.class));
    }

    @Test
    void selectEndsGameOnBotWin() throws Exception {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(model.playTurn(any())).thenReturn(TurnResult.BOT_WON);
        when(model.getStats()).thenReturn(null);
        controller(model).step(game, GUI.ACTION.SELECT);
        verify(game).setState(any(EndState.class));
    }

    @Test
    void quitReturnsToMenu() throws Exception {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        controller(model).step(game, GUI.ACTION.QUIT);
        verify(game).setState(any(MenuState.class));
    }

    @Property
    void anyActionDoesNotThrow(@ForAll GUI.ACTION action) throws Exception {
        BattleModel model = mock(BattleModel.class);
        Board enemy = mock(Board.class);
        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(new Position(0,0));
        when(enemy.getWidth()).thenReturn(5);
        when(enemy.getHeight()).thenReturn(5);
        when(model.playTurn(any())).thenReturn(TurnResult.CONTINUE);
        Game game = mock(Game.class);
        assertDoesNotThrow(() -> controller(model).step(game, action));
    }
}
