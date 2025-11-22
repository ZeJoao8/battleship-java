package battleship.viewer.game.end;

import battleship.gui.GUI;
import battleship.viewer.Viewer;

public class EndViewer extends Viewer<Boolean> {

    public EndViewer(Boolean model) {
        super(model);
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

        int col = (screenWidth - s1.length()) / 2;
        gui.drawText(col, 6,  s1, "#FFFFFF");
        gui.drawText(col, 7,  s2, "#FFFFFF");
        gui.drawText(col, 8,  s3, "#FFFFFF");
        gui.drawText(col, 9,  s4, "#FFFFFF");

        String msg = "Press Q to return to menu";
        int msgCol = (screenWidth - msg.length()) / 2;
        gui.drawText(msgCol, 11, msg, "#AAAAAA");
    }
}
