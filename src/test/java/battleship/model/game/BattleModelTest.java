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

import net.jqwik.api.*;

public class BattleModelTest {

    @Test
    void escolheBotCorretoConsoanteDificuldade() {
        assertEquals(RandomBot.class,
                new BattleModel(new Board(s(6)), new Board(s(6)), new Settings(6,6,BotDifficulty.RANDOM)).bot.getClass());
        assertEquals(HuntTargetBot.class,
                new BattleModel(new Board(s(6)), new Board(s(6)), new Settings(6,6,BotDifficulty.HUNT)).bot.getClass());
        assertEquals(Bolachas.class,
                new BattleModel(new Board(s(6)), new Board(s(6)), new Settings(6,6,BotDifficulty.BOLACHAS)).bot.getClass());
    }

    @Test
    void cursorInicial00() {
        BattleModel m = new BattleModel(new Board(s(10)),new Board(s(10)),s(10));
        assertEquals(new Position(0,0), m.getCursor());
    }

    @Test
    void setCursorFunciona() {
        BattleModel m = new BattleModel(new Board(s(10)),new Board(s(10)),s(10));
        Position novo = new Position(7,3);
        m.setCursor(novo);
        assertEquals(novo, m.getCursor());
    }

    @Test
    void alreadyShotEnemyDetetaHitOuMiss() {
        Board enemy = new Board(s(10));
        BattleModel m = new BattleModel(new Board(s(10)),enemy,s(10));
        Position p = new Position(4,4);
        assertFalse(m.alreadyShotEnemy(p));
        enemy.markMiss(p);
        assertTrue(m.alreadyShotEnemy(p));
        enemy.markHit(p);
        assertTrue(m.alreadyShotEnemy(p));
    }

    @Test
    void shootPlayerMarcaHitOuMiss() {
        Board enemy = new Board(s(10));
        enemy.placeFleetRandomly();
        Boat b = enemy.getFleet().get(0);
        Position hit = b.getPositions().get(0);
        Position miss = new Position(9,9);
        BattleModel m = new BattleModel(new Board(s(10)),enemy,s(10));
        m.shootPlayer(hit);  assertTrue(enemy.isHit(hit));
        m.shootPlayer(hit);  assertTrue(enemy.isHit(hit));
        m.shootPlayer(miss); assertTrue(enemy.isMiss(miss));
    }

    @Test
    void botDisparaNoLocalDecidido() {
        Board player = new Board(s(10));
        BotStrategy bot = mock(BotStrategy.class);
        when(bot.chooseMove(player)).thenReturn(new Position(2,2));
        BattleModel m = new BattleModel(player,new Board(s(10)),s(10));
        m.applyShot(player, bot.chooseMove(player));
        assertTrue(player.isHit(new Position(2,2)) || player.isMiss(new Position(2,2)));
        verify(bot,times(1)).chooseMove(player);
    }

    @Test
    void playerPerdeQuandoTodosNaviosAfundam() {
        Board p = new Board(s(10));
        p.placeFleetRandomly();
        for(int x=0;x<p.getWidth();x++)
            for(int y=0;y<p.getHeigth();y++)
                if(p.isShip(new Position(x,y)))
                    p.markHit(new Position(x,y));
        BattleModel m = new BattleModel(p,new Board(s(10)),s(10));
        assertTrue(m.playerLost());
    }

    @Test
    void enemyPerdeQuandoTodosNaviosAfundam() {
        Board e = new Board(s(10));
        e.placeFleetRandomly();
        for(int x=0;x<e.getWidth();x++)
            for(int y=0;y<e.getHeigth();y++)
                if(e.isShip(new Position(x,y)))
                    e.markHit(new Position(x,y));
        BattleModel m = new BattleModel(new Board(s(10)),e,s(10));
        assertTrue(m.enemyLost());
    }

    @Test
    void gettersDevolvemTabuleirosCorretos() {
        Board p = new Board(s(10));
        Board e = new Board(s(10));
        BattleModel m = new BattleModel(p,e,s(10));

        assertSame(p, m.getPlayerBoard());
        assertSame(e, m.getEnemyBoard());
    }

    @Test
    void applyShotMarcaHitOuMissDiretamente() {
        Board b = new Board(s(10));
        Position miss = new Position(0,0);
        BattleModel m = new BattleModel(b,new Board(s(10)),s(10));
        m.applyShot(b,miss);
        assertTrue(b.isMiss(miss));
        Boat boat = b.getFleet().get(0);
        b.placeBoat(boat,new Position(2,2),true);
        Position hit = boat.getPositions().get(0);
        m.applyShot(b,hit);
        assertTrue(b.isHit(hit));
    }

    @Test
    void revelaBarcoApenasQuandoAfundado() {
        Board e = new Board(s(10));
        e.placeFleetRandomly();
        Boat boat = e.getFleet().get(0);
        BattleModel m = new BattleModel(new Board(s(10)),e,s(10));
        assertFalse(m.shouldRevealBoat(boat));
        for(Position p : boat.getPositions()) e.markHit(p);
        assertTrue(m.shouldRevealBoat(boat));
    }

    @Test
    void makeBotMoveDispara() {
        Board p = new Board(s(10));
        BattleModel m = new BattleModel(p,new Board(s(10)),s(10));
        m.makeBotMove();
        boolean efetuouTiro = false;
        for(int x=0;x<10;x++)
            for(int y=0;y<10;y++)
                if(p.isHit(new Position(x,y)) || p.isMiss(new Position(x,y)))
                    efetuouTiro = true;
        assertTrue(efetuouTiro);
    }

    @Property
    void tirosDoBotSaoSempreValidos(@ForAll("posicoesValidas") Position pos) {
        Board player = new Board(s(10));
        player.markHit(pos);
        assertTrue(player.isHit(pos) || player.isMiss(pos));
    }

    @Provide
    Arbitrary<Position> posicoesValidas() {
        return Arbitraries.integers().between(0,9)
                .tuple2()
                .map(t -> new Position(t.get1(),t.get2()));
    }

    private Settings s(int n) { return new Settings(n,n,BotDifficulty.RANDOM); }
}
