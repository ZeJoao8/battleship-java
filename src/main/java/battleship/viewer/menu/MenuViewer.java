package battleship.viewer.menu;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.menu.Menu;
import battleship.viewer.Viewer;

public class MenuViewer extends Viewer<Menu> {
    public MenuViewer(Menu model) { super(model); }

    @Override
    protected void drawElements(GUI gui) {

        String s1 = "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▖ ▗▖   ▗▖ ▗▖ ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖   ";
        String s2 = "▐▌ ▐▌▐▌ ▐▌ █ ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌    ▐▛▚▖▐▌▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▌   ";
        String s3 = "▐▛▀▚▖▐▛▀▜▌ █ ▐▛▀▜▌▐▌   ▐▛▀▜▌▐▛▀▜▌    ▐▌ ▝▜▌▐▛▀▜▌▐▌  ▐▌▐▛▀▜▌▐▌   ";
        String s4 = "▐▙▄▞▘▐▌ ▐▌ █ ▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌ ▝▚▞▘ ▐▌ ▐▌▐▙▄▄▖";

        // título
        int screenWidth = gui.getWidth();
        int titleCol = (screenWidth - s1.length()) / 2;
        Position p1 = new Position(titleCol, 4);
        gui.drawText(p1, s1, "#FFFFFF");
        gui.drawText(new Position(p1.getX(),p1.getY()+1), s2, "#FFFFFF");
        gui.drawText(new Position(p1.getX(),p1.getY()+2), s3, "#FFFFFF");
        gui.drawText(new Position(p1.getX(),p1.getY()+3), s4, "#FFFFFF");

        // opções
        int startRow = 15;
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            String text = getModel().getEntry(i);
            int col = (screenWidth - text.length()) / 2;
            Position p2 = new Position(col, startRow+i*2);
            if (getModel().isSelected(i)) {
                gui.drawText(new Position(p2.getX()-2, p2.getY()), "> " + text + " <", "#00FF9D");
            } else {
                gui.drawText(p2, text, "#FFFFFF");
            }
        }
    }
}
