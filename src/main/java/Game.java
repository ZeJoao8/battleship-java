import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {
    private final Screen screen;
    private final Settings settings;

    public Game(Screen screen, Settings settings) {
        this.screen = screen;
        this.settings = settings;
    }

    // Correr sem ligar as settings (temporario)
    public Game(Screen screen) {
        this(screen, new Settings());
    }

    public void runGame() throws Exception{
        int h = settings.getHeight();
        int w = settings.getWidth();

        Board playerBoard = new Board(settings);
        Board enemyBoard  = new Board(settings);

        List<Boat> playerFleet = createFleet();
        List<Boat> enemyFleet  = createFleet();

        placeFleetRandomly(enemyBoard, enemyFleet);

        PlacementScreen placementScreen = new PlacementScreen(screen, playerBoard, playerFleet);
        boolean running = placementScreen.runPlacementScreen();

        if(!running){return;}


        BattleScreen battleScreen = new BattleScreen(screen, playerBoard, enemyBoard);
        battleScreen.runBattleScreen();
    }

    private List<Boat> createFleet() {
        List<Boat> fleet = new ArrayList<>();

        // ADAPTA isto às tuas classes concretas (construtores)
        fleet.add(new PortaAvioes(new Position(0, 0), true));
        fleet.add(new NavioTanque(new Position(0, 0), true));
        fleet.add(new Submarino(new Position(0, 0), true));
        fleet.add(new ContraTorpedeiro(new Position(0, 0), true));

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
