import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class SettingsView {

    private Settings settings;
    private SettingsController controller;

    public SettingsView(Settings settings) {
        this.settings = settings;
        this.controller = new SettingsController(settings);
    }

    public void runSettings(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();
        boolean running = true;

        while (running) {
            g.setBackgroundColor(TextColor.Factory.fromString("#1E3A5F"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            g.putString(2, 2, "Settings");

            g.putString(2, 4, "Board Size:");
            g.putString(4, 6, "- Width : " + settings.getWidth());
            g.putString(4, 7, "- Height: " + settings.getHeight());

            g.putString(2, 10, "Use W/S to increase/decrease Height");
            g.putString(2, 11, "Use A/D to increase/decrease Width");
            g.putString(2, 13, "Press Q to return to menu");

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.Character) {
                switch (Character.toLowerCase(key.getCharacter())) {
                    case 'w': controller.increaseHeight(); break;
                    case 's': controller.decreaseHeight(); break;
                    case 'a': controller.decreaseWidth(); break;
                    case 'd': controller.increaseWidth(); break;
                    case 'q': running = false; break;
                }
            } else if (key.getKeyType() == KeyType.Escape) {
                running = false;
            }
        }
    }
}