import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
public class BattleController {
    private final Screen screen;
    private final BattleModel model;
    private final BattleView view;
    private final EndScreen endScreen;

    private int cursorX = 0;
    private int cursorY = 0;

    public BattleController(Screen screen, Board playerBoard, Board enemyBoard) {
        this.screen = screen;
        this.model = new BattleModel(playerBoard, enemyBoard);
        this.view = new BattleView(screen);
        this.endScreen = new EndScreen(screen);
    }

    public void run() throws Exception {
        boolean running = true;
        Board enemyBoard = model.getEnemyBoard();

        while (running) {

            view.draw(model, cursorX, cursorY);

            // lê input
            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.ArrowUp) {
                if (cursorY > 0) cursorY--;
            } else if (key.getKeyType() == KeyType.ArrowDown) {
                if (cursorY < enemyBoard.getHeigth() - 1) cursorY++;
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                if (cursorX > 0) cursorX--;
            } else if (key.getKeyType() == KeyType.ArrowRight) {
                if (cursorX < enemyBoard.getWidth() - 1) cursorX++;
            } else if (key.getKeyType() == KeyType.Character) {
                char c = key.getCharacter();
                if (c == 'q' || c == 'Q') {
                    running = false; // volta ao menu
                }
            } else if (key.getKeyType() == KeyType.Enter) {



                if (model.isAlreadyShotEnemy(cursorX, cursorY)) {
                    continue;
                }

                model.playerShoot(cursorX, cursorY);

                if (model.hasEnemyLost()) {
                    endScreen.showEndMessage(true);
                    running = false;
                    continue;
                }

                model.botTurn();

                if (model.hasPlayerLost()) {
                    endScreen.showEndMessage(false);
                    running = false;
                }
            }
        }
    }
}
