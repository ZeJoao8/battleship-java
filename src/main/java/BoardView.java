import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public abstract class BoardView {

    protected final Screen screen;
    protected final TextGraphics g;

    protected BoardView(Screen screen) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
    }


    // desenha uma célula padrão
    protected void drawCell(int x, int y, int offsetX, int offsetY, char symbol) {
        g.putString(offsetX + x * 2, offsetY + y, String.valueOf(symbol));
    }

    // desenha um tabuleiro genérico
    protected void drawBoard(Board board, int offsetX, int offsetY, boolean hideShips) {
        int h = board.getHeigth();
        int w = board.getWidth();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int cell = board.getCell(x, y);
                char ch = cellToChar(cell, hideShips);
                String color = "#FFFFFF"; // default
                switch (cell) {
                    case Board.Water:
                        color = "#4DA6FF"; // azul claro
                        break;
                    case Board.Hit:
                        color = "#FF4444"; // vermelho vivo
                        break;
                    case Board.Miss:
                        color = "#C8C8C8"; // cinza claro
                        break;
                    case Board.Ship:
                        if (!hideShips)
                            color = "#C17F42"; // castanho do barco
                        else
                            color = "#4DA6FF"; // escondido → parece água
                        break;
                }

                g.setForegroundColor(TextColor.Factory.fromString(color));
                drawCell(x, y, offsetX, offsetY, ch);
            }
        }
    }


    // traduz uma célula para um carácter
    protected char cellToChar(int cell, boolean hideShips) {
        switch (cell) {
            case Board.Water: return '~';
            case Board.Hit:  return 'X';
            case Board.Ship:  return hideShips ? '~' : '+';
            case Board.Miss: return 'O';
            default: return '?';
        }
    }

    protected void drawCursor(int x, int y, int offsetX, int offsetY) {
        g.setForegroundColor(TextColor.Factory.fromString("#00FF9D"));
        g.putString(offsetX + x * 2, offsetY + y, "●");
    }

    // cada view terá uma forma própria de desenhar o ecrã
    public abstract void draw(Object model, int x, int y) throws Exception;
}

