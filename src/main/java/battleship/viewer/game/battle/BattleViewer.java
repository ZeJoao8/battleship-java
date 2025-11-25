package battleship.viewer.game.battle;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.battle.BattleModel;
import battleship.model.common.Board;
import battleship.model.common.Boat;
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

        gui.drawText(2, 1, "Batalha Naval - Q = voltar ao menu | Enter = disparar", "#FFFFFF");

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
        gui.drawCharacter(e.getX() + cursor.getX() * 2, e.getY() + cursor.getY(), '●', "#00FF9D");
    }
}
