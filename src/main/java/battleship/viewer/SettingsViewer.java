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
        gui.drawText(new Position(2,1), "=== SETTINGS ===", "#00FF9D");
        gui.drawText(new Position(4,4), sel == 0 ? "> Width : < " + s.getWidth()  + " >" : "  Width :   " + s.getWidth(), sel == 0 ? "#00FF9D" : "#FFFFFF");
        gui.drawText(new Position(4,6), sel == 1 ? "> Height: < " + s.getHeight() + " >" : "  Height:   " + s.getHeight(), sel == 1 ? "#00FF9D" : "#FFFFFF");
        gui.drawText(new Position(4,8), sel == 2 ? "> Bot: < " + s.getDifficulty().name() + " >" : "  Bot:   " + s.getDifficulty().name(), sel == 2 ? "#00FF9D" : "#FFFFFF");
        gui.drawText(new Position(2,11), "Use ↑/↓ escolher | ←/→ alterar", "#AAAAAA");
        gui.drawText(new Position(2,13), "Campos: Width / Height / Bot", "#AAAAAA");
        gui.drawText(new Position(2,15), "Q volta ao menu", "#AAAAAA");
    }
}
