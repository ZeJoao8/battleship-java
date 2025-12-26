package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.GameStats;
import battleship.states.game.EndState;
import battleship.viewer.Viewer;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;
import battleship.viewer.ViewerUtils;

public class EndViewer extends Viewer<Boolean> {

    private final GameStats stats;
    private final EndState state;

    public EndViewer(Boolean model, GameStats stats, EndState state) {
        super(model);
        this.stats = stats;
        this.state = state;
    }

    @Override
    protected void drawElements(GUI gui) {
        if (state.isShowingBoards()) {
            drawFinalBoards(gui);
        } else {
            drawStats(gui);
        }
        drawVictoryOrDefeat(gui);
    }

    private void drawVictoryOrDefeat(GUI gui) {
        boolean won = getModel();
        String[] title = won ? victoryTitle() : defeatTitle();
        for (int i = 0; i < title.length; i++) {
            gui.drawText(new Position(ViewerUtils.centerX(gui, title[i]), 5 + i), title[i], "#FFFFFF");
        }
    }

    private String[] victoryTitle() {
        return new String[]{
                "▗▖  ▗▖▗▄▄▄▖ ▗▄▄▖▗▄▄▄▖▗▄▖ ▗▄▄▖▗▖  ▗▖",
                "▐▌  ▐▌  █  ▐▌     █ ▐▌ ▐▌▐▌ ▐▌▝▚▞▘ ",
                "▐▌  ▐▌  █  ▐▌     █ ▐▌ ▐▌▐▛▀▚▖ ▐▌  ",
                " ▝▚▞▘ ▗▄█▄▖▝▚▄▄▖  █ ▝▚▄▞▘▐▌ ▐▌ ▐▌  "
        };
    }

    private String[] defeatTitle() {
        return new String[]{
                "▗▄▄▄ ▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖ ▗▄▖▗▄▄▄▖",
                "▐▌  █▐▌   ▐▌   ▐▌   ▐▌ ▐▌ █  ",
                "▐▌  █▐▛▀▀▘▐▛▀▀▘▐▛▀▀▘▐▛▀▜▌ █  ",
                "▐▙▄▄▀▐▙▄▄▖▐▌   ▐▙▄▄▖▐▌ ▐▌ █  "
        };
    }

    private void drawStats(GUI gui) {
        int y = 14;

        gui.drawText(new Position(ViewerUtils.centerX(gui, "===== GAME STATS ====="), y++), "===== GAME STATS =====", "#00FF9D");
        y++;
        gui.drawText(new Position(ViewerUtils.centerX(gui, "Player shots: " + stats.getPlayerShots()), y++), "Player shots: " + stats.getPlayerShots(), "#FFFFFF");
        gui.drawText(new Position(ViewerUtils.centerX(gui, "Player hits: " + stats.getPlayerHits()), y++), "Player hits: " + stats.getPlayerHits(), "#FFFFFF");
        String accP = String.format("Player precision: %.1f%%", stats.getPlayerAccuracy());
        gui.drawText(new Position(ViewerUtils.centerX(gui, accP), y++), accP, "#FFFFFF");
        y++;
        gui.drawText(new Position(ViewerUtils.centerX(gui, "Bot shots: " + stats.getBotShots()), y++), "Bot shots: " + stats.getBotShots(), "#FFFFFF");
        gui.drawText(new Position(ViewerUtils.centerX(gui, "Bot hits: " + stats.getBotHits()), y++), "Bot hits: " + stats.getBotHits(), "#FFFFFF");
        String accB = String.format("Bot precision: %.1f%%", stats.getBotAccuracy());
        gui.drawText(new Position(ViewerUtils.centerX(gui, accB), y++), accB, "#FFFFFF");
        y += 2;
        String dur = "Duration: " + stats.getDurationSeconds() + "s";
        gui.drawText(new Position(ViewerUtils.centerX(gui, dur), y++), dur, "#FFFFFF");
        y += 2;
        String hint = "Q = return | T = view final boards";
        gui.drawText(new Position(ViewerUtils.centerX(gui, hint), y), hint, "#AAAAAA");
    }

    private void drawFinalBoards(GUI gui) {
        BattleModel model = stats.getFinalModel();
        int boardW = model.getPlayerBoard().getWidth() * 2;
        int boardH = model.getPlayerBoard().getHeight();
        int gap = 8;
        int totalW = boardW * 2 + gap;
        int startX = (gui.getWidth() - totalW) / 2;
        int startY = ViewerUtils.centerY(gui, boardH);

        String title = "===== FINAL BOARDS =====";
        gui.drawText(new Position(ViewerUtils.centerX(gui, title), startY - 4), title, "#00FF9D");
        String hint = "Q = return | T = view statistics";
        gui.drawText(new Position(ViewerUtils.centerX(gui, hint), startY + boardH + 2), hint, "#AAAAAA");

        Position pPos = new Position(startX, startY);
        Position ePos = new Position(startX + boardW + gap, startY);
        new BoardViewer(model.getPlayerBoard()).draw(gui, pPos);
        model.getPlayerBoard().getFleet().forEach(b -> new BoatViewer(b).draw(gui, pPos));
        new BoardViewer(model.getEnemyBoard()).draw(gui, ePos);
        model.getEnemyBoard().getFleet().forEach(b -> new BoatViewer(b).draw(gui, ePos));

        String pLabel = " Player ";
        String eLabel = " Enemy ";
        gui.drawText(new Position(pPos.getX() + (boardW - pLabel.length()) / 2, pPos.getY() - 2), pLabel, "#00FF9D");
        gui.drawText(new Position(ePos.getX() + (boardW - eLabel.length()) / 2, ePos.getY() - 2), eLabel, "#FF4444");
    }
}
