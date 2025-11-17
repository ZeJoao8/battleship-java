package BattleShip.Controller;

import BattleShip.Model.Board;
import BattleShip.Model.Settings;
import com.googlecode.lanterna.screen.Screen;

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

        BattleController battleController = new BattleController(screen, playerBoard, enemyBoard);
        battleController.run();
    }

}
