package BattleShip.View;

import BattleShip.Model.Board;
import BattleShip.Model.Boat;
import BattleShip.Model.PlacementModel;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class PlacementBoardView extends BoardView {

    public PlacementBoardView(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Object m, int x, int y) throws Exception {
        PlacementModel model = (PlacementModel) m;
        Board board = model.getBoard();
        Boat current = board.getCurrentBoat();

        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        drawBoard(board, x, y, false);

        for (Boat b : board.getFleet()) {
            BoatView.drawBoat(b, x, y, g);
        }

        if (current != null && current.getPositions().isEmpty()) {
            if (current.isHorizontal()) {
                int lastX = model.getCursorX() + current.getSize() - 1;
                if (lastX >= model.getBoard().getWidth()) {
                    int overflow = lastX - (model.getBoard().getWidth() - 1);
                    for (int i = 0; i < overflow; i++) {
                        model.moveLeft();
                    }
                }
            }
            BoatView.drawPreview(current, model.getCursorX(), model.getCursorY(), x, y, g);
        }
        screen.refresh();
    }

}
