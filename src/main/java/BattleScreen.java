import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.util.Random;

public class BattleScreen {

    private final Screen screen;
    private final TextGraphics g;

    private final Board playerBoard;
    private final Board enemyBoard;

    // cursor para escolher onde disparar no tabuleiro inimigo
    private int cursorX = 0;
    private int cursorY = 0;

    public BattleScreen(Screen screen, Board playerBoard, Board enemyBoard) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
    }

    public void runBattleScreen() throws Exception {
        boolean running = true;

        while (running) {
            draw();

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
                // disparar no tabuleiro inimigo
                int cell = enemyBoard.getCell(cursorX, cursorY);
                if (cell == Board.Hit || cell == Board.Miss){
                    continue;
                }
                if (cell == Board.Water) {
                    enemyBoard.markMiss(cursorX, cursorY);

                    // TODO: turno do bot aqui
                } else if (cell == Board.Ship) {
                    enemyBoard.markHit(cursorX, cursorY);

                    // TODO: verificar se afundou navio, se ganhou, etc.
                }

                screen.refresh();

                if (enemyBoard.hasLost()){
                    showEndMessage("VITÓRIA!!");
                    running = false;
                    continue;
                }
                botTurn();

                if (playerBoard.hasLost()) {
                    showEndMessage("DERROTA");
                    running = false;
                    continue;
                }
            }
        }
    }

    private void draw() throws Exception {
        TerminalSize size = screen.getTerminalSize();

        g.setBackgroundColor(TextColor.Factory.fromString("#001122"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        g.fillRectangle(new TerminalPosition(0, 0), size, ' ');

        g.putString(2, 1, "Batalha Naval - Q = voltar ao menu | Enter = disparar");

        int offsetPlayerX = 2;
        int offsetPlayerY = 4;
        int offsetEnemyX = offsetPlayerX + playerBoard.getWidth() * 2 + 6;
        int offsetEnemyY = 4;

        g.putString(offsetPlayerX, offsetPlayerY - 2, "O teu tabuleiro");
        g.putString(offsetEnemyX, offsetEnemyY - 2, "Tabuleiro inimigo");

        drawBoard(playerBoard, offsetPlayerX, offsetPlayerY, false);
        drawBoard(enemyBoard, offsetEnemyX, offsetEnemyY, true);

        screen.refresh();
    }

    private void drawBoard(Board board, int offsetX, int offsetY, boolean hideShips) {
        int h = board.getHeigth();
        int w = board.getWidth();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                char ch = '·';
                int cell = board.getCell(x, y);

                if (cell == Board.Water) ch = '·';
                if (cell == Board.Ship) ch = hideShips ? '·' : 'O';
                if (cell == Board.Hit) ch = 'X';
                if (cell == Board.Miss) ch = 'o';

                boolean cursorHere = (hideShips && x == cursorX && y == cursorY);

                if (cursorHere) {
                    g.setForegroundColor(TextColor.Factory.fromString("#00FF9D"));
                    g.putString(offsetX + x * 2, offsetY + y, String.valueOf(ch));
                    g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                } else {
                    g.putString(offsetX + x * 2, offsetY + y, String.valueOf(ch));
                }
            }
        }
    }

    private void botTurn() throws Exception{
        Random rnd = new Random();
        int w = playerBoard.getWidth();
        int h = playerBoard.getHeigth();

        int x,y;

        do {
                x = rnd.nextInt(w);
                y = rnd.nextInt(h);
            } while (!playerBoard.isValidBotTarget(x, y));

            int cell = playerBoard.getCell(x, y);

            if (cell == Board.Ship) {
                playerBoard.markHit(x, y);
            } else {
                playerBoard.markMiss(x, y);
        }

        screen.refresh();
    }
    private void showEndMessage(String msg) throws Exception{
        g.setBackgroundColor(TextColor.Factory.fromString("#001122"));
        g.setForegroundColor(TextColor.ANSI.WHITE);
        g.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        g.putString(5, 5, msg);
        g.putString(6, 6,"Pressiona Q para voltar ao menu");

        screen.refresh();

        while(true) {
            KeyStroke key = screen.readInput();
            if (key!= null && key.getKeyType() == KeyType.Character) {
                char c= key.getCharacter();
                if (c == 'q' || c == 'Q') return;
            }
        }
    }

}