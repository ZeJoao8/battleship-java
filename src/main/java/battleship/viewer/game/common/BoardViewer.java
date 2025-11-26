package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.CellState;
import battleship.viewer.game.ElementViewer;

public class BoardViewer extends ElementViewer<Board> {

    public BoardViewer(Board board) {
        super(board);
    }

    @Override
    public void draw(GUI gui, Position pos) {
        Board board = getModel();
        if (board == null) return;
        for (int y = 0; y < board.getHeigth(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Position p = new Position(x, y);
                CellState cs = board.getState(p);
                gui.drawCharacter(new Position(pos.getX() + x * 2, pos.getY() + y), cellToChar(cs), cellToColor(cs));
            }
        }
    }

    private char cellToChar(CellState cs) {
        return switch (cs) {
            case HIT -> 'X';
            case MISS -> 'O';
            default -> '~';
        };
    }

    private String cellToColor(CellState cs) {
        return switch (cs) {
            case HIT -> "#FF4444";
            case MISS -> "#C8C8C8";
            default -> "#4DA6FF";
        };
    }
}
