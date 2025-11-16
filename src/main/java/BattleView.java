import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
public class BattleView {
    private final Screen screen;
    private final TextGraphics g;

    public BattleView(Screen screen) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
    }

    public void draw(BattleModel model, int cursorX, int cursorY) throws Exception {
        Board playerBoard = model.getPlayerBoard();
        Board enemyBoard = model.getEnemyBoard();

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

        drawBoard(playerBoard, offsetPlayerX, offsetPlayerY, false, -1, -1);
        drawBoard(enemyBoard, offsetEnemyX, offsetEnemyY, true, cursorX, cursorY);

        screen.refresh();
    }
    private void drawBoard(Board board, int offsetX, int offsetY, boolean hideShips, int cursorX, int cursorY) {
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

    public void showEndMessage(String msg) throws Exception{
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
