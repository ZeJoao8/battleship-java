package BattleShip.View;

import BattleShip.Model.BattleModel;
import BattleShip.Model.Board;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
public class BattleView {
    private final Screen screen;
    private final TextGraphics g;

    public BattleView(Screen screen) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
    }

    public void draw(BattleModel model, int cursorX, int cursorY) throws Exception {
        Board playerBoard = model.getPlayerBoard();
        Board enemyBoard = model.getEnemyBoard();
        FriendlyBoardView friendlyBoardView = new FriendlyBoardView(screen);
        EnemyBoardView enemyBoardView = new EnemyBoardView(screen);

        TerminalSize size = screen.getTerminalSize();

        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        g.fillRectangle(new TerminalPosition(0, 0), size, ' ');

        g.putString(2, 1, "Batalha Naval - Q = voltar ao menu | Enter = disparar");

        int offsetPlayerX = 2;
        int offsetPlayerY = 4;
        int offsetEnemyX = offsetPlayerX + playerBoard.getWidth() * 2 + 6;
        int offsetEnemyY = 4;

        g.putString(offsetPlayerX, offsetPlayerY - 2, "O teu tabuleiro");
        g.putString(offsetEnemyX, offsetEnemyY - 2, "Tabuleiro inimigo");

        friendlyBoardView.draw(model, offsetPlayerX,offsetPlayerY);
        enemyBoardView.draw(model, offsetEnemyX,offsetEnemyY);
        enemyBoardView.drawCursor(cursorX, cursorY, offsetEnemyX, offsetEnemyY);

        screen.refresh();
    }
}
