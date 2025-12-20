package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.Settings;
import battleship.viewer.ViewerUtils;

public class SettingsViewer extends Viewer<Settings> {

    public SettingsViewer(Settings model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        drawTitle(gui);
        drawOptions(gui);
        drawInstructions(gui);
    }

    private void drawTitle(GUI gui) {
        String[] t = {
                " ▗▄▄▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖▗▖  ▗▖ ▗▄▄▖ ▗▄▄▖",
                "▐▌   ▐▌     █    █    █  ▐▛▚▖▐▌▐▌   ▐▌   ",
                " ▝▀▚▖▐▛▀▀▘  █    █    █  ▐▌ ▝▜▌▐▌▝▜▌ ▝▀▚▖",
                "▗▄▄▞▘▐▙▄▄▖  █    █  ▗▄█▄▖▐▌  ▐▌▝▚▄▞▘▗▄▄▞▘"
        };
        for (int i = 0; i < t.length; i++) {
            gui.drawText(new Position(ViewerUtils.centerX(gui, t[i]), 6 + i), t[i], "#FFFFFF");
        }
    }

    private void drawOptions(GUI gui) {
        Settings s = getModel();
        int sel = s.getSelector();
        int y = 14;
        drawOption(gui, y, sel == 0, "Width: " + s.getWidth(), "> Width : < " + s.getWidth() + " >");
        y += 2;
        drawOption(gui, y, sel == 1, "Height: " + s.getHeight(), "> Height: < " + s.getHeight() + " >");
        y += 2;
        drawOption(gui, y, sel == 2, "Bot: " + s.getDifficulty().name(), "> Bot: < " + s.getDifficulty().name() + " >");
    }

    private void drawOption(GUI gui, int y, boolean selected, String normal, String highlighted) {
        String text = selected ? highlighted : normal;
        String color = selected ? "#00FF9D" : "#FFFFFF";
        gui.drawText(new Position(ViewerUtils.centerX(gui, text), y), text, color);
    }

    private void drawInstructions(GUI gui) {
        String h1 = "Use ↑/↓ to choose field | ←/→ to change value";
        String h2 = "Q = return";
        int y = 22;
        gui.drawText(new Position(ViewerUtils.centerX(gui, h1), y), h1, "#AAAAAA");
        gui.drawText(new Position(ViewerUtils.centerX(gui, h2), y + 2), h2, "#AAAAAA");
    }
}
