package battleship.viewer.battle;


import battleship.gui.GUI;
import battleship.model.battle.BattleModel;
import battleship.model.common.Board;
import battleship.viewer.common.BoardViewer;
import battleship.viewer.common.BoatViewer;
import battleship.viewer.Viewer;

public class BattleViewer extends Viewer<BattleModel> {
    public BattleViewer(BattleModel model) { super(model); }

    @Override
    protected void drawElements(GUI gui) {
        BattleModel model = getModel();
        Board player = model == null ? null : model.getPlayerBoard();
        Board enemy  = model == null ? null : model.getEnemyBoard();


        gui.drawText(2, 1, "Batalha Naval - Q = voltar ao menu | Enter = disparar", "#FFFFFF");

        int offsetPlayerX = 2;
        int offsetPlayerY = 4;
        int offsetEnemyX = offsetPlayerX + player.getWidth() * 2 + 6;
        int offsetEnemyY = 4;

        BoardViewer.drawBoard(gui, player, offsetPlayerX, offsetPlayerY);
        BoardViewer.drawBoard(gui, enemy, offsetEnemyX, offsetEnemyY);

        for (var b : player.getFleet()) BoatViewer.drawBoatGUI(b, offsetPlayerX, offsetPlayerY, gui);

        for (var b : enemy.getFleet()) {
            if (b.isSunk()) BoatViewer.drawSunkBoatGUI(b, offsetEnemyX, offsetEnemyY, gui);
            else {
                for (int i = 0; i < b.getPositions().size(); i++) {
                    if (b.isPartHit(i)) {
                        var p = b.getPositions().get(i);
                        gui.drawCharacter(offsetEnemyX + p.getX() * 2, offsetEnemyY + p.getY(), 'X', "#FF5555");
                    }
                }
            }
        }

        int cursorX = model.getViewCursorX();
        int cursorY = model.getViewCursorY();
        gui.drawCharacter(offsetEnemyX + cursorX * 2, offsetEnemyY + cursorY, '●', "#00FF9D");
    }
}
