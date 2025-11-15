import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Menu {

    private enum State { MENU, GAME, SETTINGS, EXIT }



    public void start() {
        try {
            DefaultTerminalFactory factory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(130, 42));

            Screen screen = factory.createScreen();
            screen.startScreen();

            State state = State.MENU;

            while (state != State.EXIT) {
                switch (state) {
                    case MENU -> state = runMenu(screen);
                    case GAME -> {
                        Game game = new Game(screen);
                        game.run();
                        state = runGame(screen);
                    }
                    case SETTINGS -> state = runSettings(screen);
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
        int selected = 0; // 0 = Play, 1 = Settings, 2 = Quit
        String[] options = {"Play", "Settings", "Quit"};

        while (true) {
            // fundo
            g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            // título
            String s1 = "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▖ ▗▖   ▗▖ ▗▖ ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖   ";
            String s2 = "▐▌ ▐▌▐▌ ▐▌ █ ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌    ▐▛▚▖▐▌▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▌   ";
            String s3 = "▐▛▀▚▖▐▛▀▜▌ █ ▐▛▀▜▌▐▌   ▐▛▀▜▌▐▛▀▜▌    ▐▌ ▝▜▌▐▛▀▜▌▐▌  ▐▌▐▛▀▜▌▐▌   ";
            String s4 = "▐▌▄▞▘▐▌ ▐▌ █ ▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌ ▝▚▞▘ ▐▌ ▐▌▐▙▄▄▖";

            int titleCol = (size.getColumns() - s1.length()) / 2;
            g.putString(titleCol, 4, s1);
            g.putString(titleCol, 5, s2);
            g.putString(titleCol, 6, s3);
            g.putString(titleCol, 7, s4);

            // opções
            int startRow = 15;
            for (int i = 0; i < options.length; i++) {
                String text = options[i];
                int col = (size.getColumns() - text.length()) / 2;
                if (i == selected) {
                    g.setForegroundColor(TextColor.Factory.fromString("#00FF9D")); // verde
                    g.putString(col - 2, startRow + i * 2, "> " + text + " <");
                    g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                } else {
                    g.putString(col, startRow + i * 2, text);
                }
            }

            screen.refresh();


            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.ArrowDown) {
                selected = (selected + 1) % options.length;
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                selected = (selected - 1 + options.length) % options.length;
            } else if (key.getKeyType() == KeyType.Enter) {
                return switch (selected) {
                    case 0 -> State.GAME;
                    case 1 -> State.SETTINGS;
                    default -> State.EXIT;
                };
            } else if (key.getKeyType() == KeyType.EOF || key.getKeyType() == KeyType.Escape ) {
                return State.EXIT;
            } else if(key.getKeyType() == KeyType.Character) {
                if ( key.getCharacter() == 'q' || key.getCharacter() == 'Q') {
                    return State.EXIT;
                }
            }
        }
    }

    // ---------------- GAME ----------------

    private State runGame(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();

        // usa o teu Board – ajusta se o construtor for diferente
        Board board = new Board(new Settings()); // por agora fixo 10x10

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

    // ---------------- SETTINGS ----------------

    private State runSettings(Screen screen) throws Exception {
        TextGraphics g = screen.newTextGraphics();
        boolean running = true;

        while (running) {
            g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
            g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            TerminalSize size = screen.getTerminalSize();
            g.fillRectangle(screen.getCursorPosition(), size, ' ');

            g.putString(2, 2, "Settings (ainda por implementar)");
            g.putString(2, 4, "Aqui depois vais mudar:");
            g.putString(4, 6, "- Tamanho do tabuleiro");
            g.putString(4, 7, "- Dificuldade");
            g.putString(2, 10, "Carrega em Q para voltar ao menu");

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

