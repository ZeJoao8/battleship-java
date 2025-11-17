package BattleShip.View;

import BattleShip.Controller.SettingsController;
import BattleShip.Model.Settings;
import BattleShip.util.Selector;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class SettingsView {
    private final Settings settings;
    private final SettingsController controller;
    private final Selector selector;

    public SettingsView(Settings settings) {
        this.settings = settings;
        this.controller = new SettingsController(settings);
        this.selector = new Selector(2); // 0 = Width, 1 = Height
    }

    public void runSettings(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();
        boolean running = true;

        while (running) {
            // Fundo azul suave
            g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            // Título
            g.putString(2, 2, "=== SETTINGS ===");

            // Width
            if (selector.getSelectedIndex() == 0) {
                g.setForegroundColor(TextColor.Factory.fromString("#00FF9D")); // verde
                g.putString(4, 5, "Width : < " + settings.getWidth()+" >");
            } else {
                g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF")); // branco
                g.putString(4, 5, "Width : " + settings.getWidth());
            }

            // Height
            if (selector.getSelectedIndex() == 1) {
                g.setForegroundColor(TextColor.Factory.fromString("#00FF9D")); // verde
                g.putString(4, 6, "Height: < " + settings.getHeight()+" >");
            } else {
                g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF")); // branco
                g.putString(4, 6, "Height: " + settings.getHeight());
            }

            // Instruções
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            g.putString(2, 9, "Use UP/DOWN to select field");
            g.putString(2, 10, "Use LEFT/RIGHT to change value");
            g.putString(2, 12, "Max Size: 14x14");
            g.putString(2, 13, "Min Size: 6x6");
            g.putString(2, 15, "Press Q to return to menu");

            screen.refresh();

            // Input
            KeyStroke key = screen.readInput();
            if (key == null) continue;

            switch (key.getKeyType()) {
                case ArrowUp -> selector.previous();
                case ArrowDown -> selector.next();
                case ArrowLeft -> {
                    if (selector.getSelectedIndex() == 0) controller.decreaseWidth();
                    else controller.decreaseHeight();
                }
                case ArrowRight -> {
                    if (selector.getSelectedIndex() == 0) controller.increaseWidth();
                    else controller.increaseHeight();
                }
                case Character -> {
                    if (Character.toLowerCase(key.getCharacter()) == 'q') running = false;
                }
                case Escape, EOF -> running = false;
            }
        }
    }
}