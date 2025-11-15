import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Menu {

    enum State {MENU, GAME, SETTINGS, EXIT}

    private Settings settings;
    private SettingsView settingsView;
    Selector menuSelector = new Selector(3);

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
                    case MENU -> state = runMenu(screen);
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

    // ---------------- MENU ----------------

    private State runMenu(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();
        String[] options = {"Play", "Settings", "Quit"};

        while (true) {
            g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            String s1 = "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▖ ▗▖   ▗▖ ▗▖ ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖   ";
            String s2 = "▐▌ ▐▌▐▌ ▐▌ █ ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌    ▐▛▚▖▐▌▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▌   ";
            String s3 = "▐▛▀▚▖▐▛▀▜▌ █ ▐▛▀▜▌▐▌   ▐▛▀▜▌▐▛▀▜▌    ▐▌ ▝▜▌▐▛▀▜▌▐▌  ▐▌▐▛▀▜▌▐▌   ";
            String s4 = "▐▌▄▞▘▐▌ ▐▌ █ ▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌ ▝▚▞▘ ▐▌ ▐▌▐▙▄▄▖";

            int titleCol = (size.getColumns() - s1.length()) / 2;
            g.putString(titleCol, 4, s1);
            g.putString(titleCol, 5, s2);
            g.putString(titleCol, 6, s3);
            g.putString(titleCol, 7, s4);

            int startRow = 15;
            for (int i = 0; i < options.length; i++) {
                String text = options[i];
                int col = (size.getColumns() - text.length()) / 2;
                if (menuSelector.getSelectedIndex() == i) {
                    g.setForegroundColor(TextColor.Factory.fromString("#00FF9D")); // verde
                    g.putString(col - 2, startRow + i * 2, "> " + text + " <");
                } else {
                    g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF")); // branco
                    g.putString(col, startRow + i * 2, text);
                }
            }

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            switch (key.getKeyType()) {
                case ArrowDown -> menuSelector.next();
                case ArrowUp -> menuSelector.previous();
                case Enter -> {
                    switch (menuSelector.getSelectedIndex()) {
                        case 0 -> { return State.GAME; }
                        case 1 -> { return State.SETTINGS; }
                        default -> { return State.EXIT; }
                    }
                }
                case Escape, EOF -> { return State.EXIT; }
                case Character -> {
                    if (Character.toLowerCase(key.getCharacter()) == 'q') return State.EXIT;
                }
            }
        }
    }

// ---------------- GAME ----------------

    private State runGame(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();

        Board board = new Board(settings);

        boolean running = true;

        while (running) {
            // fundo
            g.setBackgroundColor(TextColor.Factory.fromString("#001122"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            // título
            g.putString(2, 1, "Jogo - Batalha Naval (Q - voltar ao menu)");

            // desenhar tabuleiro simples (só água por enquanto)
            int offsetX = 5;
            int offsetY = 3;

            for (int y = 0; y < board.getHeigth(); y++) {
                for (int x = 0; x < board.getWidth(); x++) {
                    // mais tarde, lê board.getCell(y, x) para saber se é navio/água/tiro
                    g.putString(offsetX + x * 2, offsetY + y, "·");
                }
            }

            screen.refresh();

            // input – por agora só tecla para voltar
            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.Character) {
                if (key.getCharacter() == 'q' || key.getCharacter() == 'Q') {
                    running = false; // volta ao menu
                }
            } else if (key.getKeyType() == KeyType.Escape) {
                running = false;
            }
        }

        return State.MENU;
    }
}


