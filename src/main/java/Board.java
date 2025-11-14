import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Board {
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




}
