package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.viewer.Viewer;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;

public class BattleViewer extends Viewer<BattleModel> {

    public BattleViewer(BattleModel model) { super(model); }

    @Override
    protected void drawElements(GUI gui) {

        BattleModel model = getModel();
        Board player = model.getPlayerBoard();
        Board enemy  = model.getEnemyBoard();

        gui.drawText(new Position(2,1), "Batalha Naval - Q = voltar ao menu | Enter = disparar", "#FFFFFF");

        Position p = new Position(2, 4);
        Position e = new Position(p.getX() + player.getWidth() * 2 + 6, p.getY());
        new BoardViewer(player).draw(gui, p);
        new BoardViewer(enemy).draw(gui, e);
        for (Boat b : player.getFleet()) {
            new BoatViewer(b).draw(gui, p);
        }
        for (Boat b : enemy.getFleet()) {
            if (model.shouldRevealBoat(b)) {
                new BoatViewer(b).draw(gui, e);
            }
        }

        Position cursor = model.getCursor();
        int cx = e.getX() + cursor.getX() * 2;
        int cy = e.getY() + cursor.getY();
        boolean already = enemy.isHit(cursor) || enemy.isMiss(cursor);
        String color = already ? "#FF4444" : "#00FF9D";
        gui.drawCharacter(new Position(cx - 1, cy), '[', color);
        gui.drawCharacter(new Position(cx + 1, cy), ']', color);

    }
}
