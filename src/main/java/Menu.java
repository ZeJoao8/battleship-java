import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;

public class Menu {

    enum State {MENU, GAME, SETTINGS, EXIT}

    private Settings settings;
    private SettingsView settingsView;
    private MenuView menuView;
    private MenuController menuController;

    public Menu() {
        menuView = new MenuView();
        menuController = new MenuController(menuView);
    }

    public void start() {
        try {
            DefaultTerminalFactory factory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(130, 42));

            Screen screen = factory.createScreen();
            screen.startScreen();

            State state = State.MENU;
            settings = new Settings();
            settingsView = new SettingsView(settings);

            while (state != State.EXIT) {
                switch (state) {
                    case MENU -> {
                        menuView.drawMenu(screen);
                        KeyStroke key = screen.readInput();
                        state = menuController.handleInput(key);
                    }
                    case GAME -> state = runGame(screen);
                    case SETTINGS -> {
                        settingsView.runSettings(screen);
                        state = State.MENU;
                    }
                }
            }

            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- GAME ----------------

    private State runGame(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();
        Board board = new Board(settings);

        boolean running = true;

        while (running) {
            g.setBackgroundColor(TextColor.Factory.fromString("#001122"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            g.putString(2, 1, "Jogo - Batalha Naval (Q - voltar ao menu)");

            int offsetX = 5;
            int offsetY = 3;
            for (int y = 0; y < board.getHeigth(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {
                    g.putString(offsetX + x * 2, offsetY + y, "·");
                }
            }

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.Character) {
                if (key.getCharacter() == 'q' || key.getCharacter() == 'Q') {
                    running = false;
                }
            } else if (key.getKeyType() == KeyType.Escape) {
                running = false;
            }
        }

        return State.MENU;
    }
}


