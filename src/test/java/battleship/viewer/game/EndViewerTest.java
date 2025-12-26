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

    @Test
    void drawsBoardsWhenBoardsVisible() throws Exception {
        GUI gui = mock(GUI.class);

        Board p = new Board(new Settings());
        Board e = new Board(new Settings());
        BattleModel model = new BattleModel(p, e, new Random(), new GameStats(null));

        GameStats stats = new GameStats(null);
        stats.setFinalModel(model);

        EndState state = new EndState(true, stats);
        state.toggleBoards();

        EndViewer v = new EndViewer(true, stats, state);
        v.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
    }
}
