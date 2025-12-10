package battleship.gui;

import battleship.model.Position;

import java.io.IOException;

public interface GUI {
    enum ACTION {
        UP, DOWN, LEFT, RIGHT,
        SELECT, QUIT, AUTO, UNDO,
        ROTATE, SHOW_BOARD, NONE
    }

    ACTION getNextAction() throws IOException;

    int getWidth();

    int getHeight();

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    void drawText(Position p, String text, String color);

    void drawCharacter(Position p, char c, String color);

    void fillBackground(String bgColor);
}

