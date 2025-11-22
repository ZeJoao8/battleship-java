package battleship.viewer.menu;

import battleship.gui.GUI;
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
        gui.drawText(titleCol, 4, s1, "#FFFFFF");
        gui.drawText(titleCol, 5, s2, "#FFFFFF");
        gui.drawText(titleCol, 6, s3, "#FFFFFF");
        gui.drawText(titleCol, 7, s4, "#FFFFFF");

        // opções
        int startRow = 15;
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            String text = getModel().getEntry(i);
            int col = (screenWidth - text.length()) / 2;
            if (getModel().isSelected(i)) {
                gui.drawText(col - 2, startRow + i * 2, "> " + text + " <", "#00FF9D");
            } else {
                gui.drawText(col, startRow + i * 2, text, "#FFFFFF");
            }
        }
    }
}
