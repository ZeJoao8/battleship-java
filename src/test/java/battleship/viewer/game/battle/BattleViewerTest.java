package battleship.viewer.game.battle;

import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.game.BattleModel;
import battleship.model.game.GameStats;
import battleship.model.game.common.Board;
import battleship.model.game.common.bot.Random;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BattleViewerTest {

    @Test
    void drawsBattleScreen() throws Exception {
        GUI gui = mock(GUI.class);

        Board p = new Board(new Settings());
        Board e = new Board(new Settings());
        BattleModel model = new BattleModel(p, e, new Random(), new GameStats(null));

        BattleViewer viewer = new BattleViewer(model);
        viewer.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
    }
}
