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

        // header
        gui.drawText(new Position(2,1), "=== SETTINGS ===", "#00FF9D");

        // width / height
        gui.drawText(new Position(4,4), sel == 0 ? "> Width : < " + s.getWidth()  + " >" : "  Width :   " + s.getWidth(), sel == 0 ? "#00FF9D" : "#FFFFFF");
        gui.drawText(new Position(4,6), sel == 1 ? "> Height: < " + s.getHeight() + " >" : "  Height:   " + s.getHeight(), sel == 1 ? "#00FF9D" : "#FFFFFF");

        gui.drawText(new Position(2,9), "Use ↑/↓ para escolher campo, ←/→ para alterar valor", "#AAAAAA");
        gui.drawText(new Position(2,11), "Min: 6x6 | Max: 14x14", "#AAAAAA");
        gui.drawText(new Position(2,13), "Press Q para voltar ao menu", "#AAAAAA");
    }
}
