package battleship.viewer;

import battleship.gui.GUI;
import battleship.model.Settings;
import battleship.model.Settings.BotDifficulty;
import battleship.model.Position;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SettingsViewerTest {

    @Test
    void drawsTitleAndDefaultValues() {
        GUI gui = mock(GUI.class);
        Settings s = new Settings();
        s.setWidth(10);
        s.setHeight(6);
        s.setDifficulty(BotDifficulty.HUNT);
        SettingsViewer viewer = new SettingsViewer(s);
        viewer.drawElements(gui);
        verify(gui).drawText(new Position(2,1), "=== SETTINGS ===", "#00FF9D");
        verify(gui).drawText(new Position(4,4), "> Width : < 10 >", "#00FF9D");
        verify(gui).drawText(new Position(4,6), "  Height:   6",    "#FFFFFF");
        verify(gui).drawText(new Position(4,8), "  Bot:   HUNT",     "#FFFFFF");
        verify(gui).drawText(new Position(2,11), "Use ↑/↓ escolher | ←/→ alterar", "#AAAAAA");
        verify(gui).drawText(new Position(2,13), "Campos: Width / Height / Bot",   "#AAAAAA");
        verify(gui).drawText(new Position(2,15), "Q volta ao menu",                "#AAAAAA");
    }

    @Test
    void selectorHeightHighlightsCorrectLine() {
        GUI gui = mock(GUI.class);
        Settings s = new Settings();
        s.setWidth(5);
        s.setHeight(9);
        s.setDifficulty(BotDifficulty.RANDOM);
        s.setSelector(1);
        new SettingsViewer(s).drawElements(gui);
        verify(gui).drawText(new Position(4,4), "  Width :   5",      "#FFFFFF");
        verify(gui).drawText(new Position(4,6), "> Height: < 9 >",    "#00FF9D");
        verify(gui).drawText(new Position(4,8), "  Bot:   RANDOM",    "#FFFFFF");
    }

    @Test
    void selectorBotHighlightsCorrectLine() {
        GUI gui = mock(GUI.class);
        Settings s = new Settings();
        s.setWidth(7);
        s.setHeight(7);
        s.setDifficulty(BotDifficulty.HUNT);
        s.setSelector(2);
        new SettingsViewer(s).drawElements(gui);
        verify(gui).drawText(new Position(4,4), "  Width :   7",      "#FFFFFF");
        verify(gui).drawText(new Position(4,6), "  Height:   7",      "#FFFFFF");
        verify(gui).drawText(new Position(4,8), "> Bot: < HUNT >",    "#00FF9D");
    }
}
