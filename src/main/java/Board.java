import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.List;

public class Board {

    private static final int Water = 0;
    private static final int Ship = 1;
    private static final int Hit = 2;
    private static final int Miss = 3;

    private int heigth;
    private int width;
    private int[][] board;

    public Board(Settings settings) {
        this.heigth = settings.getHeight();
        this.width = settings.getWidth();
        this.board = new int[width][heigth];
    }

    public int getHeigth() {
        return heigth;
    }
    public int getWidth(){
        return width;
    }

    public boolean inside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < heigth;
    }
    public int getCell(int x, int y){ return board[x][y]; }
    public void setCell(int x, int y, int value){ board[x][y] = value; }

    public boolean canPlaceBoat(Boat boat, int x, int y, boolean horizontal){
        int tamanho = boat.getSize();

        for (int i=0; i < tamanho; i++){
            if (horizontal){
                int col = x + i;
                if (!inside(col, y)){ return false; }
                if (getCell(col, y) != Water) return false;
            } else {
                int row = y + i;
                if (!inside(x, row)){ return false; }
                if (getCell(x, row) != Water) return false;
            }
        }
        return true;
    }

    public void placeBoat(Boat boat, int x, int y, boolean horizontal) {
        if (canPlaceBoat(boat, x, y, horizontal)) {
            int tamanho = boat.getSize();

            for (int i = 0; i < tamanho; i++) {
                int col = x + (horizontal ? i : 0);
                int row = y + (horizontal ? 0 : i);
                setCell(col, row, Ship);
                boat.addPosition(new Position(col, row));
            }
        }
    }

    public void markHit(int x, int y) { board[y][x] = Hit; }
    public void markMiss(int x, int y) { board[y][x] = Miss; }
}
