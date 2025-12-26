package battleship.model.game;

import battleship.controller.game.TurnResult;
import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.BotStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BattleModelTest {

    private BattleModel makeModel(Board player, Board enemy, BotStrategy bot) {
        return new BattleModel(player, enemy, bot, new GameStats(null));
    }

    private Board board() {
        return new Board(new Settings(5,5, Settings.BotDifficulty.EASY));
    }

    @Test
    void cursorStartsAtZeroZero() {
        BattleModel m = makeModel(board(), board(), mock(BotStrategy.class));
        assertEquals(new Position(0,0), m.getCursor());
    }

    @Test
    void setCursorUpdatesCursor() {
        BattleModel m = makeModel(board(), board(), mock(BotStrategy.class));
        Position p = new Position(2,3);
        m.setCursor(p);
        assertEquals(p, m.getCursor());
    }

    @Test
    void alreadyShotEnemyDetectsMissAndHit() {
        Board enemy = board();
        enemy.markMiss(new Position(1,1));
        enemy.markHit(new Position(2,2));

        BattleModel m = makeModel(board(), enemy, mock(BotStrategy.class));

        assertTrue(m.alreadyShotEnemy(new Position(1,1)));
        assertTrue(m.alreadyShotEnemy(new Position(2,2)));
        assertFalse(m.alreadyShotEnemy(new Position(0,0)));
    }

    @Test
    void playTurnReturnsContinueIfAlreadyShot() {
        Board enemy = board();
        Position p = new Position(1,1);
        enemy.markMiss(p);

        BattleModel m = makeModel(board(), enemy, mock(BotStrategy.class));

        assertEquals(TurnResult.CONTINUE, m.playTurn(p));
    }

    @Test
    void botMoveIsExecuted() {
        Board player = board();
        BotStrategy bot = mock(BotStrategy.class);
        Position shot = new Position(0,0);

        when(bot.chooseMove(player)).thenReturn(shot);

        BattleModel m = makeModel(player, board(), bot);
        m.makeBotMove();

        verify(bot).chooseMove(player);
        assertTrue(player.isMiss(shot) || player.isHit(shot));
    }
}
