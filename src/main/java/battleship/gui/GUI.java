package battleship.gui;

import battleship.model.Position;
import java.io.IOException;

public interface GUI {
    enum ACTION {
        UP, DOWN, LEFT, RIGHT,
        SELECT, QUIT, ROTATE, NONE
    }

    ACTION getNextAction() throws IOException;

    int getWidth();

    int getHeight();

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    void drawText(int x, int y, String text, String color);

    void drawCharacter(int x, int y, char c, String color);

    void fillBackground(String bgColor);
}

