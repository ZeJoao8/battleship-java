import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class PlacementView {
    private final Screen screen;
    private final TextGraphics g;

    public PlacementView(Screen screen) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
    }

    public void draw(PlacementModel model) throws Exception {
        Board board = model.getBoard();
        Boat current = model.getCurrentBoat();

        // fundo
        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        TerminalSize size = screen.getTerminalSize();
        g.fillRectangle(new TerminalPosition(0,0), size, ' ');

        // cabeçalho / instruções
        g.putString(2, 1, "Posicione a sua frota - (Q = sair)");
        g.putString(2, 3, "A colocar: " + current.getName()
                + " (tamanho " + current.getSize() + ")  |  Orientação: "
                + (model.isHorizontal() ? "Horizontal" : "Vertical"));
        g.putString(2, 4, "Setas = mover  |  R = rodar  |  Enter = colocar");

        int offsetX = 5;
        int offsetY = 6;

        int h = board.getHeigth();
        int w = board.getWidth();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                char ch = '·';
                int cell = board.getCell(x, y);
                if (cell == Board.Ship) ch = 'O';

                if (isPartOfPreview(board, model, current, x, y)) {
                    ch = '#';
                }

                // cursor
                if (x == model.getCursorX() && y == model.getCursorY()) {
                    g.setForegroundColor(TextColor.Factory.fromString("#00FF9D"));
                    g.putString(offsetX + x * 2, offsetY + y, String.valueOf(ch));
                    g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                } else {
                    g.putString(offsetX + x * 2, offsetY + y, String.valueOf(ch));
                }
            }
        }

        screen.refresh();
    }

    private boolean isPartOfPreview(Board board, PlacementModel model, Boat boat, int x, int y) {
        if (!boat.getPositions().isEmpty()) return false;

        int len = boat.getSize();
        for (int i = 0; i < len; i++) {
            int bx = model.getCursorX() + (model.isHorizontal() ? i : 0);
            int by = model.getCursorY() + (model.isHorizontal() ? 0 : i);

            if (!board.inside(bx, by)) continue;
            if (bx == x && by == y) return true;
        }
        return false;
    }
}

