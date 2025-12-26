package battleship.viewer.game.battle;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;

public class BattleBoardViewer {

    private final Board board;
    private final BattleModel model;
    private final Position cursor;

    public BattleBoardViewer(Board board, BattleModel model, Position cursor) {
        this.board = board;
        this.model = model;
        this.cursor = cursor;
    }

    public void draw(GUI gui, Position pos) {
        drawBoard(gui, pos);
        drawBoats(gui, pos);
        drawCursor(gui, pos);
    }

    private void drawBoard(GUI gui, Position pos) {
        new BoardViewer(board).draw(gui, pos);
    }

    private void drawBoats(GUI gui, Position pos) {
        for (Boat b : board.getFleet()) {
            if (model == null) {
                new BoatViewer(b).draw(gui, pos);
            }
            else if (model.isEnemyBoatVisible(b)) {
                new BoatViewer(b).draw(gui, pos);
            }
        }
    }

    private void drawCursor(GUI gui, Position base) {
        if (cursor == null) return;
        int cx = base.getX() + cursor.getX() * 2;
        int cy = base.getY() + cursor.getY();
        gui.drawCharacter(new Position(cx - 1, cy), '[', "#00FF9D");
        gui.drawCharacter(new Position(cx + 1, cy), ']', "#00FF9D");
    }
}
