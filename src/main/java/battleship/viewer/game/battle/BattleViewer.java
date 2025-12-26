package battleship.viewer.game.battle;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.viewer.Viewer;
import battleship.viewer.ViewerUtils;

public class BattleViewer extends Viewer<BattleModel> {

    private Position playerPos;
    private Position enemyPos;
    private int boardPixelW;
    private int boardPixelH;

    public BattleViewer(battleship.model.game.BattleModel model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        drawTitle(gui);
        drawBoards(gui);
        drawLabels(gui);
        drawInstructions(gui);
        drawTimer(gui);
    }

    private void drawTitle(GUI gui) {
        String[] t = {
                "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▄▄▖▗▖   ▗▄▄▄▖",
                "▐▌ ▐▌▐▌ ▐▌ █    █  ▐▌   ▐▌   ",
                "▐▛▀▚▖▐▛▀▜▌ █    █  ▐▌   ▐▛▀▀▘",
                "▐▙▄▞▘▐▌ ▐▌ █    █  ▐▙▄▄▖▐▙▄▄▖"
        };
        for (int i = 0; i < t.length; i++) {
            gui.drawText(new Position(ViewerUtils.centerX(gui, t[i]), 6 + i), t[i], "#FFFFFF");
        }
    }

    private void drawBoards(GUI gui) {
        BattleModel model = getModel();
        boardPixelW = model.getPlayerBoard().getWidth() * 2;
        boardPixelH = model.getPlayerBoard().getHeight();
        int gap = 6;
        int totalW = boardPixelW * 2 + gap;
        int startX = (gui.getWidth() - totalW) / 2;
        int startY = ViewerUtils.centerY(gui, boardPixelH);
        playerPos = new Position(startX, startY);
        enemyPos  = new Position(startX + boardPixelW + gap, startY);
        new BattleBoardViewer(model.getPlayerBoard(), null, null).draw(gui, playerPos);
        new BattleBoardViewer(model.getEnemyBoard(), model, model.getCursor()).draw(gui, enemyPos);
    }

    private void drawLabels(GUI gui) {
        String playerLabel = " Player ";
        String enemyLabel  = " Enemy  ";
        int y = playerPos.getY() - 2;
        int px = playerPos.getX() + (boardPixelW - playerLabel.length()) / 2;
        int ex = enemyPos.getX()  + (boardPixelW - enemyLabel.length()) / 2;
        gui.drawText(new Position(px, y), playerLabel, "#00FF9D");
        gui.drawText(new Position(ex, y), enemyLabel,  "#FF4444");
    }

    private void drawInstructions(GUI gui) {
        String s = "Q = return | ENTER = shoot";
        gui.drawText(new Position(ViewerUtils.centerX(gui, s), playerPos.getY() + boardPixelH + 2), s, "#AAAAAA");
    }

    private void drawTimer(GUI gui) {
        String t = "Time: " + getModel().getStats().getDurationSeconds() + "s";
        int x = playerPos.getX() + boardPixelW + 3 - t.length() / 2;
        int y = playerPos.getY() - 3;
        gui.drawText(new Position(x, y), t, "#FFFFFF");
    }
}
