package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.common.Board;
import battleship.model.common.CellState;

public class BoardViewer {

    public static void drawBoard(GUI gui, Board board, int offsetX, int offsetY) {
        if (board == null) return;
        for (int y = 0; y < board.getHeigth(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Position p = new Position(x, y);
                CellState cs = board.getState(p);
                gui.drawCharacter(offsetX + x * 2, offsetY + y, cellToChar(cs), cellToColor(cs)
                );
            }
        }
    }

    private static char cellToChar(CellState cs) {
        return switch (cs) {
            case HIT -> 'X';
            case MISS -> 'O';
            default -> '~';
        };
    }

    private static String cellToColor(CellState cs) {
        return switch (cs) {
            case HIT -> "#FF4444";
            case MISS -> "#C8C8C8";
            default -> "#4DA6FF";
        };
    }
}
