import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final Screen screen;
    private final Settings settings;

    public Game(Screen screen, Settings settings) {
        this.screen = screen;
        this.settings = settings;
    }

    public void runGame() throws Exception {
        int h = settings.getHeight();
        int w = settings.getWidth();

        Board playerBoard = new Board(settings);
        Board enemyBoard  = new Board(settings);

        enemyBoard.placeFleetRandomly();

        PlacementController placement = new PlacementController(screen, playerBoard);
        boolean running = placement.run();

        if (!running) return;

        BattleScreen battleScreen = new BattleScreen(screen, playerBoard, enemyBoard);
        battleScreen.runBattleScreen();
    }

}
