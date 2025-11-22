package battleship.viewer.common;

import battleship.gui.GUI;
import battleship.model.common.Board;

public class BoardViewer {

    public static void drawBoard(GUI gui, Board board, int offsetX, int offsetY) {
        if (board == null) return;

        for (int y = 0; y < board.getHeigth(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                int cell = board.getCell(x, y);
                char ch = cellToChar(cell);
                String color = cellToColor(cell);

                gui.drawCharacter(offsetX + x * 2, offsetY + y, ch, color);
            }
        }
    }

    private static char cellToChar(int cell) {
        switch (cell) {
            case Board.Hit:   return 'X';
            case Board.Miss:  return 'O';
            default:          return '~';
        }
    }

    private static String cellToColor(int cell) {
        switch (cell) {
            case Board.Hit:   return "#FF4444";
            case Board.Miss:  return "#C8C8C8";
            default:          return "#4DA6FF";
        }
    }
}
