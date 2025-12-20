package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.states.MenuState;
import battleship.states.State;
import battleship.states.game.EndState;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BattleControllerTest {

    @Provide
    Arbitrary<GUI.ACTION> cursorActions() {
        return Arbitraries.of(GUI.ACTION.UP, GUI.ACTION.DOWN, GUI.ACTION.LEFT, GUI.ACTION.RIGHT);
    }

    @Property
    void cursorMovesCorrectly(@ForAll("cursorActions") GUI.ACTION action) {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemy = mock(Board.class);

        Position cursor = mock(Position.class);
        Position moved = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemy);
        when(model.getCursor()).thenReturn(cursor);

        switch(action) {
            case UP -> when(cursor.moveUpBounded()).thenReturn(moved);
            case LEFT -> when(cursor.moveLeftBounded()).thenReturn(moved);
            case DOWN -> {
                when(enemy.getHeigth()).thenReturn(10);
                when(cursor.moveDownBounded(9)).thenReturn(moved);
            }
            case RIGHT -> {
                when(enemy.getWidth()).thenReturn(10);
                when(cursor.moveRightBounded(9)).thenReturn(moved);
            }
        }

        new BattleController(model).step(game, action);
        verify(model).setCursor(moved);
    }

    @Test
    void stepQuit_changesStateToMenuState() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.QUIT);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(MenuState.class, stateCaptor.getValue());
    }

    @Test
    void select_doesNothingIfAlreadyShot() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);
        when(model.alreadyShotEnemy(cursor)).thenReturn(true);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.SELECT);

        verify(model, never()).playerShoot(any());
        verify(model, never()).makeBotMove();
        verify(game, never()).setState(any());
    }

    @Test
    void select_enemyLoses_setsEndStateAndNoBotMove() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);
        when(model.alreadyShotEnemy(cursor)).thenReturn(false);
        when(model.enemyLost()).thenReturn(true);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.SELECT);

        verify(model).playerShoot(cursor);
        verify(model, never()).makeBotMove();

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(EndState.class, stateCaptor.getValue());
    }

    @Test
    void select_enemyNotLost_playerNotLost_noStateChange() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);
        when(model.alreadyShotEnemy(cursor)).thenReturn(false);
        when(model.enemyLost()).thenReturn(false);
        when(model.playerLost()).thenReturn(false);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.SELECT);

        verify(model).playerShoot(cursor);
        verify(model).makeBotMove();
        verify(game, never()).setState(any());
    }

    @Test
    void select_playerLoses_setsEndStateAfterBotMove() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);
        when(model.alreadyShotEnemy(cursor)).thenReturn(false);
        when(model.enemyLost()).thenReturn(false);
        when(model.playerLost()).thenReturn(true);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.SELECT);

        verify(model).playerShoot(cursor);
        verify(model).makeBotMove();

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(EndState.class, stateCaptor.getValue());
    }
}