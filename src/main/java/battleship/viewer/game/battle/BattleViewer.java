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

        int xP = 2;
        int yP = 4;
        int xE = xP + player.getWidth() * 2 + 6;
        int yE = 4;

        BoardViewer.drawBoard(gui, player, xP, yP);
        BoardViewer.drawBoard(gui, enemy,  xE, yE);

        for (Boat b : player.getFleet()) {BoatViewer.drawBoatGUI(b, xP, yP, gui);}
        for (Boat b : enemy.getFleet()){if (b.isSunk()) {BoatViewer.drawBoatGUI(b, xE, yE, gui);}}
        Position cursor = model.getCursor();
        gui.drawCharacter(xE + cursor.getX() * 2, yE + cursor.getY(), '●', "#00FF9D");
    }
}
