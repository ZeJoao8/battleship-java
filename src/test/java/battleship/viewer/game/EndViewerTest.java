package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.game.BattleModel;
import battleship.model.game.GameStats;
import battleship.model.game.common.Board;
import battleship.model.game.common.bot.Random;
import battleship.states.game.EndState;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EndViewerTest {

    private BattleModel makeBattleModel() {
        Board player = new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
        Board enemy  = new Board(new Settings(6,6, Settings.BotDifficulty.EASY));
        return new BattleModel(player, enemy, new Random(), new GameStats(null));
    }

    @Test
    void drawsStatsWhenBoardsAreHidden() throws Exception {
        GUI gui = mock(GUI.class);
        BattleModel model = makeBattleModel();
        GameStats stats = new GameStats(null);
        stats.setFinalModel(model);
        EndState state = new EndState(true, stats);
        EndViewer viewer = new EndViewer(false, stats, state);
        viewer.draw(gui);
        verify(gui).clear();
        verify(gui).refresh();
        verify(gui, atLeastOnce()).drawText(any(), anyString(), anyString());
    }

    @Test
    void drawsFinalBoardsWhenBoardsAreVisible() throws Exception {
        GUI gui = mock(GUI.class);
        BattleModel model = makeBattleModel();
        GameStats stats = new GameStats(null);
        stats.setFinalModel(model);
        EndState state = new EndState(true, stats);
        state.toggleBoards();
        EndViewer viewer = new EndViewer(true, stats, state);
        viewer.draw(gui);
        verify(gui).clear();
        verify(gui).refresh();
        verify(gui, atLeastOnce()).drawText(any(), anyString(), anyString());
    }
}
