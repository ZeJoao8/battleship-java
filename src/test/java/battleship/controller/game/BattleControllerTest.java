package battleship.controller.game;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.states.MenuState;
import battleship.states.State;
import battleship.states.game.EndState;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BattleControllerTest {

    // --------- movimentos do cursor ---------

    @Test
    void stepUp_movesCursorUp() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);
        Position movedUp = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);
        when(cursor.moveUpBounded()).thenReturn(movedUp);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.UP);

        verify(model).setCursor(movedUp);
    }

    @Test
    void stepLeft_movesCursorLeft() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);
        Position movedLeft = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(model.getCursor()).thenReturn(cursor);
        when(cursor.moveLeftBounded()).thenReturn(movedLeft);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.LEFT);

        verify(model).setCursor(movedLeft);
    }

    @Test
    void stepDown_movesCursorDownWithinBounds() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);
        Position movedDown = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(enemyBoard.getHeigth()).thenReturn(10);
        when(model.getCursor()).thenReturn(cursor);
        when(cursor.moveDownBounded(9)).thenReturn(movedDown);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.DOWN);

        verify(model).setCursor(movedDown);
    }

    @Test
    void stepRight_movesCursorRightWithinBounds() {
        BattleModel model = mock(BattleModel.class);
        Game game = mock(Game.class);
        Board enemyBoard = mock(Board.class);
        Position cursor = mock(Position.class);
        Position movedRight = mock(Position.class);

        when(model.getEnemyBoard()).thenReturn(enemyBoard);
        when(enemyBoard.getWidth()).thenReturn(10);
        when(model.getCursor()).thenReturn(cursor);
        when(cursor.moveRightBounded(9)).thenReturn(movedRight);

        BattleController controller = new BattleController(model);

        controller.step(game, GUI.ACTION.RIGHT);

        verify(model).setCursor(movedRight);
    }

    // --------- QUIT ---------

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

    // --------- SELECT / handleSelect ---------

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

        verify(model, never()).shootPlayer(any());
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

        verify(model).shootPlayer(cursor);
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

        verify(model).shootPlayer(cursor);
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

        verify(model).shootPlayer(cursor);
        verify(model).makeBotMove();

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(game).setState(stateCaptor.capture());

        assertInstanceOf(EndState.class, stateCaptor.getValue());
    }
}