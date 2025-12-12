package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.Settings;

public class SettingsViewer extends Viewer<Settings> {

    public SettingsViewer(Settings model) { super(model); }

    @Override
    protected void drawElements(GUI gui) {
        Settings s = getModel();
        int sel = s.getSelector();

        String t1 = " ▗▄▄▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖▗▖  ▗▖ ▗▄▄▖ ▗▄▄▖";
        String t2 = "▐▌   ▐▌     █    █    █  ▐▛▚▖▐▌▐▌   ▐▌   ";
        String t3 = " ▝▀▚▖▐▛▀▀▘  █    █    █  ▐▌ ▝▜▌▐▌▝▜▌ ▝▀▚▖";
        String t4 = "▗▄▄▞▘▐▙▄▄▖  █    █  ▗▄█▄▖▐▌  ▐▌▝▚▄▞▘▗▄▄▞▘";
        int y=6;
        gui.drawText(new Position(centerX(gui, t1), y++), t1, "#FFFFFF");
        gui.drawText(new Position(centerX(gui, t2), y++), t2, "#FFFFFF");
        gui.drawText(new Position(centerX(gui, t3), y++), t3, "#FFFFFF");
        gui.drawText(new Position(centerX(gui, t4), y++), t4, "#FFFFFF");

        String op1 = sel == 0 ? "> Width : < " + s.getWidth()  + " >" : "Width: " + s.getWidth();
        String op2 = sel == 1 ? "> Height: < " + s.getHeight() + " >" : "Height: " + s.getHeight();
        String op3 = sel == 2 ? "> Bot: < " + s.getDifficulty().name() + " >" : "Bot: " + s.getDifficulty().name();
        y=14;
        gui.drawText(new Position(centerX(gui, op1), y++),  op1, sel == 0 ? "#00FF9D" : "#FFFFFF");
        y++;
        gui.drawText(new Position(centerX(gui, op2), y++),  op2, sel == 1 ? "#00FF9D" : "#FFFFFF");
        y++;
        gui.drawText(new Position(centerX(gui, op3), y++), op3, sel == 2 ? "#00FF9D" : "#FFFFFF");
        String h1 = "Use ↑/↓ to choose field | ←/→ to change value";
        String h2 = "Q = return to menu";
        y++; y++;
        gui.drawText(new Position(centerX(gui, h1), y++), h1, "#AAAAAA");
        y++; y++;
        gui.drawText(new Position(centerX(gui, h2), y), h2, "#AAAAAA");
    }
}
