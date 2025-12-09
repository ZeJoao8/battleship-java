package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.GameStats;
import battleship.viewer.Viewer;

public class EndViewer extends Viewer<Boolean> {

    private final GameStats stats;

    public EndViewer(Boolean model, GameStats stats) {
        super(model);
        this.stats = stats;
    }

    private int centerX(String text, int screenWidth) {
        return (screenWidth - text.length()) / 2;
    }

    @Override
    protected void drawElements(GUI gui) {

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

        String msg = "Press Q to return to menu";
        gui.drawText(new Position(centerX(msg, screenWidth), 11), msg, "#AAAAAA");

        int y = 14;
        String t;
        t = "===== GAME STATS =====";
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#00FF9D");
        y++;

        t = "Tiros do jogador: " + stats.getPlayerShots();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        t = "Acertos do jogador: " + stats.getPlayerHits();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");
        t = String.format("Precisão do jogador: %.1f%%", stats.getPlayerAccuracy());
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#FFFFFF");

        y++;
        t = "Tiros do bot: " + stats.getBotShots();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#AAAAAA");
        t = "Acertos do bot: " + stats.getBotHits();
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#AAAAAA");
        t = String.format("Precisão do bot: %.1f%%", stats.getBotAccuracy());
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#AAAAAA");
        y++;

        t = "Duração: " + stats.getDurationSeconds() + "s";
        gui.drawText(new Position(centerX(t, screenWidth), y++), t, "#CCCCCC");
    }
}
