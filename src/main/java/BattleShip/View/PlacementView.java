package BattleShip.View;

import BattleShip.Model.Board;
import BattleShip.Model.Boat;
import BattleShip.Model.PlacementModel;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class PlacementView {

    private final Screen screen;
    private final TextGraphics g;
    private final PlacementBoardView view;
    public PlacementView(Screen screen) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
        this.view = new PlacementBoardView(screen);
    }

    public void draw(PlacementModel model) throws Exception {
        Board board = model.getBoard();
        Boat current = board.getCurrentBoat();

        clearScreen();
        drawHeader(current);
        int x=5;
        int y=6;

        view.draw(model,x,y);

        screen.refresh();
    }

    private void clearScreen() {
        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        TerminalSize size = screen.getTerminalSize();
        g.fillRectangle(new TerminalPosition(0,0), size, ' ');
    }

    private void drawHeader(Boat current) {
        g.putString(2, 1, "Posicione a sua frota - (Q = sair)");
        g.putString(2, 3, "A colocar: " + (current != null ? current.getName() : "Nenhum")
                + " (tamanho " + (current != null ? current.getSize() : "-") + ")  |  Orientação: "
                + (current != null && current.isHorizontal() ? "Horizontal" : "Vertical"));
        g.putString(2, 4, "Setas = mover  |  R = rodar  |  Enter = colocar");
    }
}


