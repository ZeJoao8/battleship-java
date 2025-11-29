package battleship.model.game.common.bot;
import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HuntTargetBotTest {

    @Test
    void hitsShipQueuesCellsforNextShot() throws Exception {
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.HUNT));
        b.placeBoat(b.getFleet().get(0), new Position(5,5), true);
        HuntTargetBot bot = new HuntTargetBot();
        Random mockRand = mock(Random.class);
        when(mockRand.nextInt(anyInt())).thenReturn(5);
        Field rndField = HuntTargetBot.class.getDeclaredField("rnd");
        rndField.setAccessible(true);
        rndField.set(bot, mockRand);
        Position first = bot.chooseMove(b);
        assertEquals(new Position(5,5), first);
        assertTrue(b.isShip(first));
        Position second = bot.chooseMove(b);
        assertTrue(
                second.equals(new Position(6,5)) ||
                        second.equals(new Position(4,5)) ||
                        second.equals(new Position(5,6)) ||
                        second.equals(new Position(5,4))
        );
    }

    @Test
    void withEmptyQueueSelectsValidRandomTarget() {
        HuntTargetBot bot = new HuntTargetBot();
        Board b = new Board(new Settings(10,10, Settings.BotDifficulty.HUNT));
        Position p = bot.chooseMove(b);
        assertTrue(b.isValidBotTarget(p));
    }
}
