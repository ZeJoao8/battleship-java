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

    private Board realBoard() {
        return new Board(new Settings(5, 5, Settings.BotDifficulty.EASY));
    }

    private BattleModel model(Board player, Board enemy, BotStrategy bot) {
        GameTimer timer = mock(GameTimer.class);
        GameStats stats = new GameStats(timer);
        return new BattleModel(player, enemy, bot, stats);
    }

    @Test
    void cursorStartsAtZeroZero() {
        BattleModel m = model(realBoard(), realBoard(), mock(BotStrategy.class));
        assertEquals(new Position(0, 0), m.getCursor());
    }

    @Test
    void setCursorUpdatesCursor() {
        BattleModel m = model(realBoard(), realBoard(), mock(BotStrategy.class));
        Position p = new Position(2, 3);
        m.setCursor(p);
        assertEquals(p, m.getCursor());
    }

    @Test
    void alreadyShotEnemyDetectsMissAndHit() {
        Board enemy = realBoard();
        Position miss = new Position(1, 1);
        Position hit = new Position(2, 2);
        enemy.markMiss(miss);
        enemy.markHit(hit);
        BattleModel m = model(realBoard(), enemy, mock(BotStrategy.class));
        assertTrue(m.alreadyShotEnemy(miss));
        assertTrue(m.alreadyShotEnemy(hit));
        assertFalse(m.alreadyShotEnemy(new Position(0, 0)));
    }

    @Test
    void playerShootMarksMissOnWater() {
        Board enemy = spy(realBoard());
        BattleModel m = model(realBoard(), enemy, mock(BotStrategy.class));
        Position p = new Position(0, 0);
        doReturn(true).when(enemy).isWater(p);
        m.playerShoot(p);
        verify(enemy).markMiss(p);
    }

    @Test
    void playerShootMarksHitOnShip() {
        Board enemy = spy(realBoard());
        BattleModel m = model(realBoard(), enemy, mock(BotStrategy.class));
        Position p = new Position(1, 1);
        doReturn(false).when(enemy).isWater(p);
        doReturn(true).when(enemy).isShip(p);
        m.playerShoot(p);
        verify(enemy).markHit(p);
    }

    @Test
    void applyShotHitsShip() {
        Board b = spy(realBoard());
        BattleModel m = model(realBoard(), b, mock(BotStrategy.class));
        Position p = new Position(2, 2);
        doReturn(true).when(b).isShip(p);
        m.applyShot(b, p);
        verify(b).markHit(p);
    }

    @Test
    void applyShotMissesWater() {
        Board b = spy(realBoard());
        BattleModel m = model(realBoard(), b, mock(BotStrategy.class));
        Position p = new Position(3, 3);
        doReturn(false).when(b).isShip(p);
        m.applyShot(b, p);
        verify(b).markMiss(p);
    }

    @Test
    void makeBotMoveChoosesAndRegistersShot() {
        Board player = realBoard();
        BotStrategy bot = mock(BotStrategy.class);
        Position shot = new Position(0, 0);
        when(bot.chooseMove(player)).thenReturn(shot);
        BattleModel m = model(player, realBoard(), bot);
        m.makeBotMove();
        verify(bot).chooseMove(player);
        verify(bot).registerShot(player, shot);
        assertTrue(player.isHit(shot) || player.isMiss(shot));
    }

    @Test
    void playTurnReturnsContinueIfAlreadyShot() {
        Board enemy = realBoard();
        Position p = new Position(1, 1);
        enemy.markMiss(p);
        BattleModel m = model(realBoard(), enemy, mock(BotStrategy.class));
        assertEquals(TurnResult.CONTINUE, m.playTurn(p));
    }

    @Test
    void playTurnMissDoesNotEndGame() {
        Board enemy = realBoard();
        BotStrategy bot = mock(BotStrategy.class);
        when(bot.chooseMove(any())).thenReturn(new Position(0, 0));
        BattleModel m = model(realBoard(), enemy, bot);
        TurnResult r = m.playTurn(new Position(2, 2));
        assertEquals(TurnResult.CONTINUE, r);
    }

    @Test
    void playTurnPlayerWinsWhenEnemyLost() {
        Board enemy = spy(realBoard());
        BotStrategy bot = mock(BotStrategy.class);
        doReturn(true).when(enemy).hasLost();
        BattleModel m = model(realBoard(), enemy, bot);
        TurnResult r = m.playTurn(new Position(0, 0));
        assertEquals(TurnResult.PLAYER_WON, r);
    }

    @Test
    void playTurnBotWinsWhenPlayerLost() {
        Board player = spy(realBoard());
        Board enemy = realBoard();
        BotStrategy bot = mock(BotStrategy.class);
        when(bot.chooseMove(player)).thenReturn(new Position(0, 0));
        doReturn(true).when(player).hasLost();
        BattleModel m = model(player, enemy, bot);
        TurnResult r = m.playTurn(new Position(1, 1));
        assertEquals(TurnResult.BOT_WON, r);
    }
}
