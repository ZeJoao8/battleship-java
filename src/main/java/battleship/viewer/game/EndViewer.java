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
            return;
        }

        boolean won = getModel();
        String s1, s2, s3, s4;

        if(won){
            s1 = "▗▖  ▗▖▗▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▄▖▗▄▄▖ ▄   ▄";
            s2 = "▐▌  ▐▌▐▌▐▌     █  ▐▌  ▐▌▐▌ ▐▌ ▀▄▀ ";
            s3 = "▐▌  ▐▌▐▌▐▌     █  ▐▌  ▐▌▐▛▀▚▖  █  ";
            s4 = " ▝▚▞▘ ▐▌▐▙▄▄▖  █  ▐▙▄▄▟▌▐▌ ▐▌  █  ";
        }
        else{
            s1 = "▗▄▄▖ ▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖ ▗▄▖ ▗▄▄▄▖";
            s2 = "▐▌ ▐▌▐▌   ▐▌   ▐▌   ▐▌ ▐▌  █  ";
            s3 = "▐▌ ▐▌▐▛▀  ▐▛▀▀▘▐▛▀  ▐▛▀▜▌  █  ";
            s4 = "▐▙▄▞▘▐▙▄▄▖▐▌   ▐▙▄▄▖▐▌ ▐▌  █  ";
        }
        int screenWidth = gui.getWidth();
        gui.drawText(new Position(centerX(s1, screenWidth), 6), s1, "#FFFFFF");
        gui.drawText(new Position(centerX(s2, screenWidth), 7), s2, "#FFFFFF");
        gui.drawText(new Position(centerX(s3, screenWidth), 8), s3, "#FFFFFF");
        gui.drawText(new Position(centerX(s4, screenWidth), 9), s4, "#FFFFFF");

        String msg = "Press Q to return | Press T to view final boards";
        gui.drawText(new Position(centerX(msg, screenWidth), 11), msg, "#AAAAAA");

        int y = 14;
        String t;
        t = "===== GAME STATS =====";
        y++;
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#00FF9D");

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
    }

    private void drawFinalBoards(GUI gui) {
        BattleModel model = stats.getFinalModel();

        gui.drawText(new Position(2,2),
                "FINAL BOARDS — Press T to return",
                "#FFFFFF");

        int px = 4;
        int py = 5;

        new BoardViewer(model.getPlayerBoard()).draw(gui, new Position(px, py));
        for (var b : model.getPlayerBoard().getFleet())
            new BoatViewer(b).draw(gui, new Position(px, py));

        int ex = px + model.getPlayerBoard().getWidth() * 2 + 6;

        new BoardViewer(model.getEnemyBoard()).draw(gui, new Position(ex, py));
        for (var b : model.getEnemyBoard().getFleet())
            new BoatViewer(b).draw(gui, new Position(ex, py));
    }
}
