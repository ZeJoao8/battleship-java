package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Random;

class HuntTargetBotTest {

    @Test
    void quandoAcertaNavioCriaQueueUsaNaJogadaSeguinte() throws Exception {
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.HUNT));
        b.placeBoat(b.getFleet().get(0), new Position(5,5), true);
        HuntTargetBot bot = new HuntTargetBot();
        Random mockRand = mock(Random.class);
        when(mockRand.nextInt(anyInt())).thenReturn(5);
        Field rndField = HuntTargetBot.class.getDeclaredField("rnd");
        rndField.setAccessible(true);
        rndField.set(bot, mockRand);
        Position first = bot.chooseMove(b);
        assertEquals(5, first.getX());
        assertEquals(5, first.getY());
        assertTrue(b.isShip(first));
        Position second = bot.chooseMove(b);
        boolean adj =
                second.equals(new Position(6,5)) ||
                        second.equals(new Position(4,5)) ||
                        second.equals(new Position(5,6)) ||
                        second.equals(new Position(5,4));
        assertTrue(adj);
    }

    @Test
    void semQueueEscolheTiroValido() {
        HuntTargetBot bot = new HuntTargetBot();
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.HUNT));
        Position p = bot.chooseMove(b);
        assertTrue(b.isValidBotTarget(p));
    }
}
