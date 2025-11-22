package battleship.viewer.settings;


import battleship.gui.GUI;
import battleship.model.settings.Settings;
import battleship.viewer.Viewer;

public class SettingsViewer extends Viewer<Settings> {

    public SettingsViewer(Settings model) { super(model); }

    @Override
    protected void drawElements(GUI gui) {
        Settings s = getModel();
        int sel = s.getSelector();

        // header
        gui.drawText(2, 1, "=== SETTINGS ===", "#00FF9D");

        // width / height
        gui.drawText(4, 4, sel == 0 ? "> Width : < " + s.getWidth()  + " >" : "  Width :   " + s.getWidth(), sel == 0 ? "#00FF9D" : "#FFFFFF");
        gui.drawText(4, 6, sel == 1 ? "> Height: < " + s.getHeight() + " >" : "  Height:   " + s.getHeight(), sel == 1 ? "#00FF9D" : "#FFFFFF");

        gui.drawText(2, 9, "Use ↑/↓ para escolher campo, ←/→ para alterar valor", "#AAAAAA");
        gui.drawText(2, 11, "Min: 6x6 | Max: 14x14", "#AAAAAA");
        gui.drawText(2, 13, "Press Q para voltar ao menu", "#AAAAAA");
    }
}
