package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;
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
        Position text = new Position(col, 6);
        gui.drawText(text,  s1, "#FFFFFF");
        gui.drawText(new Position(text.getX(), text.getY()+1),  s2, "#FFFFFF");
        gui.drawText(new Position(text.getX(), text.getY()+2),  s3, "#FFFFFF");
        gui.drawText(new Position(text.getX(), text.getY()+3),  s4, "#FFFFFF");

        String msg = "Press Q to return to menu";
        int msgCol = (screenWidth - msg.length()) / 2;
        Position pmsg = new Position(msgCol, 11);
        gui.drawText(pmsg, msg, "#AAAAAA");
    }
}
