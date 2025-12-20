package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.Settings.BotDifficulty;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.bot.*;
import battleship.model.game.common.BotStrategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BattleModelTest {

    private Settings s(int n) { return new Settings(n,n,BotDifficulty.RANDOM); }
    private Board b(int n){ return new Board(s(n)); }

    private Board killedFleet(int n){
        Board b = b(n);
        b.placeFleetRandomly();
        for(int x=0;x<n;x++)for(int y=0;y<n;y++){
            Position p=new Position(x,y);
            if(b.isShip(p)) b.markHit(p);
        }
        return b;
    }

    @Test
    void selectsCorrectBotForDifficulty() {
        assertInstanceOf(Random.class, new BattleModel(b(6), b(6), new Settings(6,6,BotDifficulty.RANDOM)).bot);
        assertInstanceOf(Hunt.class,new BattleModel(b(6), b(6), new Settings(6,6,BotDifficulty.HUNT)).bot);
        assertInstanceOf(Perfect.class, new BattleModel(b(6), b(6), new Settings(6,6,BotDifficulty.PERFECT)).bot);
    }

    @Test
    void cursorStartsAtZeroZero() {
        BattleModel m = new BattleModel(b(10),b(10),s(10));
        assertEquals(new Position(0,0), m.getCursor());
    }

    @Test
    void cursorCanBeChangedNormally() {
        BattleModel m = new BattleModel(b(10),b(10),s(10));
        Position p = new Position(7,3);
        m.setCursor(p);
        assertEquals(p, m.getCursor());
    }

    @Test
    void cursorCanLeaveBoardWithoutException() {
        BattleModel m = new BattleModel(b(10),b(10),s(10));
        m.setCursor(new Position(99,-5));
        assertEquals(new Position(99,-5), m.getCursor());
    }

    @Test
    void alreadyShotEnemyDetectsHitOrMiss() {
        Board enemy = b(10);
        BattleModel m = new BattleModel(b(10),enemy,s(10));
        Position p = new Position(4,4);

        assertFalse(m.alreadyShotEnemy(p));
        enemy.markMiss(p);
        assertTrue(m.alreadyShotEnemy(p));
        enemy.markHit(p);
        assertTrue(m.alreadyShotEnemy(p));
    }

    @Test
    void shootPlayerAlwaysMarksCorrectHitOrMiss() {
        Board enemy = b(10);
        BattleModel m = new BattleModel(b(10),enemy,s(10));
        Boat boat = enemy.getFleet().get(0);
        enemy.placeBoat(boat,new Position(2,3),true);
        Position HIT = boat.getPositions().get(0);
        Position MISS = new Position(9,9);
        m.playerShoot(MISS); assertTrue(enemy.isMiss(MISS));
        m.playerShoot(HIT);  assertTrue(enemy.isHit(HIT));
        m.playerShoot(HIT);  assertTrue(enemy.isHit(HIT));
    }

    @Test
    void botShootsAutomaticallyDuringMakeBotMove() throws Exception {
        Board player = b(10);
        BotStrategy bot = mock(BotStrategy.class);
        when(bot.chooseMove(player)).thenReturn(new Position(3,3));
        BattleModel m = new BattleModel(player,b(10),s(10));
        var f = BattleModel.class.getDeclaredField("bot");
        f.setAccessible(true);
        f.set(m, bot);
        m.makeBotMove();
        verify(bot,times(1)).chooseMove(player);
        assertTrue(player.isHit(new Position(3,3)) || player.isMiss(new Position(3,3)));
    }

    @Test
    void playerLosesWhenAllSunk() {
        BattleModel m = new BattleModel(killedFleet(10),b(10),s(10));
        assertTrue(m.playerLost());
    }

    @Test
    void enemyLosesWhenAllSunk() {
        BattleModel m = new BattleModel(b(10),killedFleet(10),s(10));
        assertTrue(m.enemyLost());
    }

    @Test
    void revealsBoatOnlyWhenSunk() {
        Board e = b(10);
        e.placeFleetRandomly();
        Boat boat = e.getFleet().get(0);
        BattleModel m = new BattleModel(b(10),e,s(10));
        assertFalse(m.shouldRevealBoat(boat));
        for(Position pos: boat.getPositions()) e.markHit(pos);
        assertTrue(m.shouldRevealBoat(boat));
    }
}
