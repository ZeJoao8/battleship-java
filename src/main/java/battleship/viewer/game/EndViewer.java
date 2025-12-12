package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.BattleModel;
import battleship.model.game.GameStats;
import battleship.states.game.EndState;
import battleship.viewer.Viewer;
import battleship.viewer.game.common.BoardViewer;
import battleship.viewer.game.common.BoatViewer;

public class EndViewer extends Viewer<Boolean> {

    private final GameStats stats;
    private final EndState state;

    public EndViewer(Boolean model, GameStats stats, EndState state) {
        super(model);
        this.stats = stats;
        this.state = state;
    }

    private int centerX(String text, int width) {
        return (width - text.length()) / 2;
    }

    @Override
    protected void drawElements(GUI gui) {

        if (state.isShowingBoards()) {
            drawFinalBoards(gui);
        }
        else{
            drawStats(gui);
        }

        boolean won = getModel();
        String s1, s2, s3, s4;

        if(won){
            s1 = "▗▖  ▗▖▗▄▄▄▖ ▗▄▄▖▗▄▄▄▖▗▄▖ ▗▄▄▖▗▖  ▗▖";
            s2 = "▐▌  ▐▌  █  ▐▌     █ ▐▌ ▐▌▐▌ ▐▌▝▚▞▘ ";
            s3 = "▐▌  ▐▌  █  ▐▌     █ ▐▌ ▐▌▐▛▀▚▖ ▐▌  ";
            s4 = " ▝▚▞▘ ▗▄█▄▖▝▚▄▄▖  █ ▝▚▄▞▘▐▌ ▐▌ ▐▌  ";
        }
        else{
            s1 = "▗▄▄▄ ▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖ ▗▄▖▗▄▄▄▖";
            s2 = "▐▌  █▐▌   ▐▌   ▐▌   ▐▌ ▐▌ █  ";
            s3 = "▐▌  █▐▛▀▀▘▐▛▀▀▘▐▛▀▀▘▐▛▀▜▌ █  ";
            s4 = "▐▙▄▄▀▐▙▄▄▖▐▌   ▐▙▄▄▖▐▌ ▐▌ █  ";
        }
        int screenWidth = gui.getWidth();
        gui.drawText(new Position(centerX(s1, screenWidth), 6), s1, "#FFFFFF");
        gui.drawText(new Position(centerX(s2, screenWidth), 7), s2, "#FFFFFF");
        gui.drawText(new Position(centerX(s3, screenWidth), 8), s3, "#FFFFFF");
        gui.drawText(new Position(centerX(s4, screenWidth), 9), s4, "#FFFFFF");
    }

    private void drawStats(GUI gui) {
        int screenWidth = gui.getWidth();
        int y = 14;
        String t;
        t = "===== GAME STATS =====";
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#00FF9D");
        y++;
        t = "Player shots: " + stats.getPlayerShots();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        t = "Player hits: " + stats.getPlayerHits();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        t = String.format("Player precision: %.1f%%", stats.getPlayerAccuracy());
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");

        y++;
        t = "Bot shots: " + stats.getBotShots();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        t = "Bot hits: " + stats.getBotHits();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        t = String.format("Bot precision: %.1f%%", stats.getBotAccuracy());
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        y++;

        t = "Duration: " + stats.getDurationSeconds() + "s";
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        y++;y++;
        String msg = "Q = return | T = view final boards";
        gui.drawText(new Position(centerX(msg, screenWidth), y), msg, "#AAAAAA");
    }

    private void drawFinalBoards(GUI gui) {
        BattleModel model = stats.getFinalModel();

        int screenWidth = gui.getWidth();
        int screenHeight = gui.getHeight();

        String msg = "Q = return | T = view statistics";
        gui.drawText(new Position(centerX(msg, screenWidth), (screenHeight+model.getPlayerBoard().getHeigth())/2+2), msg, "#AAAAAA");
        String t = "===== FINAL BOARDS =====";
        gui.drawText(new Position(centerX(t, screenWidth), (screenHeight-model.getPlayerBoard().getHeigth())/2-2), t, "#00FF9D");
        int boardW = model.getPlayerBoard().getWidth() * 2;
        int boardH = model.getPlayerBoard().getHeigth();
        int gap = 8;

        int totalWidth = boardW + gap + boardW;

        int startX = (screenWidth - totalWidth) / 2;
        int posY = (screenHeight - boardH) / 2;

        int playerX = startX;
        int enemyX  = startX + boardW + gap;
        new BoardViewer(model.getPlayerBoard()).draw(gui, new Position(playerX, posY));
        for (var b : model.getPlayerBoard().getFleet())
            new BoatViewer(b).draw(gui, new Position(playerX, posY));

        new BoardViewer(model.getEnemyBoard()).draw(gui, new Position(enemyX, posY));
        for (var b : model.getEnemyBoard().getFleet())
            new BoatViewer(b).draw(gui, new Position(enemyX, posY));
    }
}
