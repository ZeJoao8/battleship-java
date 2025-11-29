package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomBotTest {

    private void injectMockRandom(RandomBot bot, Random mockRand) throws Exception {
        Field f = RandomBot.class.getDeclaredField("rnd");
        f.setAccessible(true);
        f.set(bot, mockRand);
    }

    @Test
    void nameAndDifficultyMatchSpecification() {
        RandomBot bot = new RandomBot();
        assertEquals("Random Bot", bot.getName());
        assertEquals("Fácil", bot.getDifficulty());
    }

    @Test
    void chooseMoveReturnsOnlyValidCells() throws Exception {
        Board board = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
        Random mockedRng = mock(Random.class);
        when(mockedRng.nextInt(anyInt())).thenReturn(3);
        RandomBot bot = new RandomBot();
        injectMockRandom(bot, mockedRng);
        Position p = bot.chooseMove(board);
        assertTrue(board.isValidBotTarget(p));
        assertEquals(new Position(3,3), p);
    }

    @Test
    void repeatsUntilValidTargetFound() throws Exception {
        Board board = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
        board.markHit(new Position(5,5));
        Random mocked = mock(Random.class);
        when(mocked.nextInt(anyInt())).thenReturn(5).thenReturn(5).thenReturn(2).thenReturn(2);
        RandomBot bot = new RandomBot();
        injectMockRandom(bot, mocked);
        Position result = bot.chooseMove(board);
        assertEquals(new Position(2,2), result);
    }

}
