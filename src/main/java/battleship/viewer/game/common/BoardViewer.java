package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.common.Board;

public class BoardViewer {

    public static void drawBoard(GUI gui, Board board, int offsetX, int offsetY) {
        if (board == null) return;

        for (int y = 0; y < board.getHeigth(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Position p = new Position(x, y);
                int cell = board.getCell(p);
                gui.drawCharacter(offsetX + p.getX() * 2, offsetY + p.getY(), cellToChar(cell), cellToColor(cell));
            }
        }
    }

    private static char cellToChar(int cell) {
        switch (cell) {
            case Board.HIT:  return 'X';
            case Board.MISS: return 'O';
            default:         return '~';
        }
    }

    private static String cellToColor(int cell) {
        switch (cell) {
            case Board.HIT:  return "#FF4444";
            case Board.MISS: return "#C8C8C8";
            default:         return "#4DA6FF";
        }
    }
}
