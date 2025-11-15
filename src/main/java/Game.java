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

        List<Boat> playerFleet = createFleet();
        List<Boat> enemyFleet  = createFleet();

        placeFleetRandomly(enemyBoard, enemyFleet);

        PlacementController placement = new PlacementController(screen, playerBoard, playerFleet);
        boolean running = placement.run();

        if (!running) return;

        BattleScreen battleScreen = new BattleScreen(screen, playerBoard, enemyBoard);
        battleScreen.runBattleScreen();
    }


    private List<Boat> createFleet() {
        List<Boat> fleet = new ArrayList<>();

        fleet.add(new PortaAvioes());
        fleet.add(new NavioTanque());
        fleet.add(new ContraTorpedeiro());
        fleet.add(new ContraTorpedeiro());
        fleet.add(new Submarino());

        return fleet;
    }


    private void placeFleetRandomly(Board board, List<Boat> fleet) {
        Random rnd = new Random();
        int h = board.getHeigth();
        int w = board.getWidth();

        for (Boat boat : fleet) {
            boolean placed = false;
            while (!placed) {
                boolean horiz = rnd.nextBoolean();
                int x = rnd.nextInt(w);
                int y = rnd.nextInt(h);
                if (board.canPlaceBoat(boat, x, y, horiz)) {
                    board.placeBoat(boat, x, y, horiz);
                    placed = true;
                }
            }
        }
    }
}
