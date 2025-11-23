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
                gui.drawCharacter(offsetX + p.getX() * 2, offsetY + p.getY(), cellToChar(board, p), cellToColor(board, p)
                );
            }
        }
    }

    private static char cellToChar(Board board, Position p) {
        if (board.isHit(p)) return 'X';
        if (board.isMiss(p)) return 'O';
        return '~';
    }

    private static String cellToColor(Board board, Position p) {
        if (board.isHit(p)) return "#FF4444";
        if (board.isMiss(p)) return "#C8C8C8";
        return "#4DA6FF";
    }
}
