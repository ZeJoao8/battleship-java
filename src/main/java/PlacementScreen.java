import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.util.List;

public class PlacementScreen {

    private final Screen screen;
    private final TextGraphics g;

    private final Board playerBoard;
    private final List<Boat> fleet;

    private int cursorX = 0;
    private int cursorY = 0;

    //private Position position = new Position(0,0);

    private boolean horizontal = true;

    public PlacementScreen(Screen screen, Board playerBoard, List<Boat> fleet) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
        this.playerBoard = playerBoard;
        this.fleet = fleet;
    }

    public boolean runPlacementScreen() throws Exception {


        while (true) {

            // Fundo azul suave
            g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(new TerminalPosition(0,0), size, ' ');


            Boat current = getCurrentBoat();

            g.putString(2, 1, "Posicione a sua frota - (Q = sair)");
            g.putString(2, 3, "A colocar: " + current.getName() +
                    " (tamanho " + current.getSize() + ")" +
                    " | Orientação: " + (horizontal ? "Horizontal" : "Vertical"));
            g.putString(2, 4, "Setas = mover | R = rodar | Enter = colocar");

            int offsetX = 5;
            int offsetY = 6;

            int h = playerBoard.getHeigth();
            int w = playerBoard.getWidth();

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {

                    char ch = '·';
                    int cell = playerBoard.getCell(x, y);
                    if (cell == Board.Ship) ch = 'O';

                    // preview do barco atual
                    if (isPartOfPreview(x, y, current)) {
                        ch = '#';
                    }

                    // cursor
                    if (x == cursorX && y == cursorY) {
                        g.setForegroundColor(TextColor.Factory.fromString("#00FF9D"));
                        g.putString(offsetX + x * 2, offsetY + y, String.valueOf(ch));
                        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                    } else {
                        g.putString(offsetX + x * 2, offsetY + y, String.valueOf(ch));
                    }
                }
            }

            screen.refresh();

            if (allBoatsPlaced()) {
                // terminou a colocação
                return true;
            }

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.ArrowUp) {
                if (cursorY > 0) cursorY--;
            } else if (key.getKeyType() == KeyType.ArrowDown) {
                if (cursorY < playerBoard.getHeigth() - 1) cursorY++;
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                if (cursorX > 0) cursorX--;
            } else if (key.getKeyType() == KeyType.ArrowRight) {
                if (cursorX < playerBoard.getWidth() - 1) cursorX++;
            } else if (key.getKeyType() == KeyType.Character) {
                char c = key.getCharacter();
                if (c == 'q' || c == 'Q') {
                    return false; // volta ao menu
                }
                if (c == 'r' || c == 'R') {
                    horizontal = !horizontal; // roda o barco
                }
            } else if (key.getKeyType() == KeyType.Enter) {

                if (playerBoard.canPlaceBoat(current, cursorX, cursorY, horizontal)) {
                    playerBoard.placeBoat(current, cursorX, cursorY, horizontal);
                } else {
                    System.out.println("Posição inválida para " + current.getName());
                }
            }

        }

    }


    private boolean allBoatsPlaced() {
        for (Boat b : fleet) {
            if (b.getPositions().isEmpty()) return false;
        }
        return true;
    }

    private Boat getCurrentBoat() {
        for (Boat b : fleet) {
            if (b.getPositions().isEmpty()) return b;
        }
        // se todos tiverem posições, devolve o último
        return fleet.get(fleet.size() - 1);
    }

    private boolean isPartOfPreview(int x, int y, Boat boat) {
        if (!boat.getPositions().isEmpty()) return false;

        int size = boat.getSize();

        for (int i = 0; i < size; i++) {
            int bx = cursorX + (horizontal ? i : 0);
            int by = cursorY + (horizontal ? 0 : i);

            if (!playerBoard.inside(bx, by)) continue;

            if (x == bx && y == by)
                return true;
        }

        return false;
    }

}
