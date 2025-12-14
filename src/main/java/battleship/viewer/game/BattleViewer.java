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
        String instruct = "Q = return | ENTER = shoot";
        gui.drawText(new Position((gui.getWidth()- instruct.length() ) / 2,(gui.getHeight() + player.getHeigth()) / 2 + 2), instruct, "#AAAAAA");

        int W = gui.getWidth();
        int H = gui.getHeight();

        int pW = player.getWidth() * 2;
        int pH = player.getHeigth();

        int eW = enemy.getWidth() * 2;
        int eH = enemy.getHeigth();

        int gap = 6;

        int totalW = pW + gap + eW;
        int totalH = Math.max(pH, eH);

        int offX = Math.max(0, (W - totalW) / 2);
        int offY = Math.max(0, (H - totalH) / 2);

        Position p = new Position(offX, offY);
        Position e = new Position(offX + pW + gap, offY);

        String playerLabel = " Player ";
        String enemyLabel  = " Enemy  ";

        int labelXPlayer = p.getX() + (pW - playerLabel.length()) / 2;
        int labelXEnemy  = e.getX() + (eW - enemyLabel.length()) / 2;
        int labelY = offY - 2;

        gui.drawText(new Position(labelXPlayer, labelY), playerLabel, "#00FF9D" );
        gui.drawText(new Position(labelXEnemy,  labelY), enemyLabel,  "#FF4444");

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

        String s1 ="▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▄▄▖▗▖   ▗▄▄▄▖";
        String s2 ="▐▌ ▐▌▐▌ ▐▌ █    █  ▐▌   ▐▌   ";
        String s3 ="▐▛▀▚▖▐▛▀▜▌ █    █  ▐▌   ▐▛▀▀▘";
        String s4 ="▐▙▄▞▘▐▌ ▐▌ █    █  ▐▙▄▄▖▐▙▄▄▖";

        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),6), s1, "#FFFFFF");
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),7), s2, "#FFFFFF");
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),8), s3, "#FFFFFF");
        gui.drawText(new Position(((gui.getWidth() - s1.length()) / 2),9), s4, "#FFFFFF");

        int time = getModel().getStats().getDurationSeconds();
        String timerText = "Time: " + time + "s";
        gui.drawText(
                new Position((gui.getWidth() - timerText.length() - 2)/2, labelY-1),
                timerText,
                "#FFFFFF"
        );
    }
}
