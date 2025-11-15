import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.util.List;

public class PlacementController {
    private final Screen screen;
    private final PlacementModel model;
    private final PlacementView view;

    public PlacementController(Screen screen, Board board, List<Boat> fleet) {
        this.screen = screen;
        this.model = new PlacementModel(board, fleet);
        this.view = new PlacementView(screen);
    }

    public boolean run() throws Exception {
        while (true) {
            view.draw(model);

            if (model.allPlaced()) return true;

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            KeyType kt = key.getKeyType();
            if (kt == KeyType.ArrowUp) {
                if (model.getCursorY() > 0) model.moveUp();
            } else if (kt == KeyType.ArrowDown) {
                if (model.getCursorY() < model.getBoard().getHeigth() - 1) model.moveDown();
            } else if (kt == KeyType.ArrowLeft) {
                if (model.getCursorX() > 0) model.moveLeft();
            } else if (kt == KeyType.ArrowRight) {
                if (model.getCursorX() < model.getBoard().getWidth() - 1) model.moveRight();
            } else if (kt == KeyType.Character) {
                char c = key.getCharacter();
                if (c == 'q' || c == 'Q') return false;
                if (c == 'r' || c == 'R') model.rotate();
            } else if (kt == KeyType.Enter) {
                Boat current = model.getCurrentBoat();
                int x = model.getCursorX();
                int y = model.getCursorY();
                boolean horiz = model.isHorizontal();
                if (model.getBoard().canPlaceBoat(current, x, y, horiz)) {
                    model.getBoard().placeBoat(current, x, y, horiz);
                } else {
                    System.out.println("Posição inválida para " + current.getName());
                }
            } else if (kt == KeyType.Escape || kt == KeyType.EOF) {
                return false;
            }
        }
    }
}

