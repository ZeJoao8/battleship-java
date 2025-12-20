package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Position;
import battleship.viewer.ViewerUtils;

public class MenuViewer extends Viewer<Menu> {

    public MenuViewer(Menu model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        drawTitle(gui);
        drawOptions(gui);
    }

    private void drawTitle(GUI gui) {
        String[] t = {
                "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▖ ▗▖   ▗▖ ▗▖ ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖   ",
                "▐▌ ▐▌▐▌ ▐▌ █ ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌    ▐▛▚▖▐▌▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▌   ",
                "▐▛▀▚▖▐▛▀▜▌ █ ▐▛▀▜▌▐▌   ▐▛▀▜▌▐▛▀▜▌    ▐▌ ▝▜▌▐▛▀▜▌▐▌  ▐▌▐▛▀▜▌▐▌   ",
                "▐▙▄▞▘▐▌ ▐▌ █ ▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌ ▝▚▞▘ ▐▌ ▐▌▐▙▄▄▖"
        };
        for (int i = 0; i < t.length; i++) {
            gui.drawText(new Position(ViewerUtils.centerX(gui, t[i]), 6 + i), t[i], "#FFFFFF");
        }
    }

    private void drawOptions(GUI gui) {
        int startRow = 15;
        int width = gui.getWidth();
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            drawOption(gui, i, startRow, width);
        }
    }

    private void drawOption(GUI gui, int idx, int startRow, int width) {
        String text = getModel().getEntry(idx);
        int x = (width - text.length()) / 2;
        int y = startRow + idx * 2;
        if (getModel().isSelected(idx)) {
            gui.drawText(new Position(x - 2, y), "> " + text + " <", "#00FF9D");
        }
        else {
            gui.drawText(new Position(x, y), text, "#FFFFFF");
        }
    }
}
