package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomBotTest {

    private void injectMockRandom(Random bot, java.util.Random mockRand) throws Exception {
        Field f = Random.class.getDeclaredField("rnd");
        f.setAccessible(true);
        f.set(bot, mockRand);
    }

    @Test
    void nameAndDifficultyMatchSpecification() {
        Random bot = new Random();
        assertEquals("Random Bot", bot.getName());
        assertEquals("Fácil", bot.getDifficulty());
    }

    @Test
    void chooseMoveReturnsOnlyValidCells() throws Exception {
        Board board = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
        java.util.Random mockedRng = mock(java.util.Random.class);
        when(mockedRng.nextInt(anyInt())).thenReturn(3);
        Random bot = new Random();
        injectMockRandom(bot, mockedRng);
        Position p = bot.chooseMove(board);
        assertTrue(board.isValidBotTarget(p));
        assertEquals(new Position(3,3), p);
    }

    @Test
    void repeatsUntilValidTargetFound() throws Exception {
        Board board = new Board(new Settings(10,10, Settings.BotDifficulty.RANDOM));
        board.markHit(new Position(5,5));
        java.util.Random mocked = mock(java.util.Random.class);
        when(mocked.nextInt(anyInt())).thenReturn(5).thenReturn(5).thenReturn(2).thenReturn(2);
        Random bot = new Random();
        injectMockRandom(bot, mocked);
        Position result = bot.chooseMove(board);
        assertEquals(new Position(2,2), result);
    }

}
