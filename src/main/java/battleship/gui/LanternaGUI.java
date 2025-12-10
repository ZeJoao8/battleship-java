package battleship.gui;

import battleship.model.Position;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class LanternaGUI implements GUI {
    private final Screen screen;

    public LanternaGUI(int width, int height) throws IOException {
        TerminalSize size = new TerminalSize(width, height);
        DefaultTerminalFactory factory = new DefaultTerminalFactory()
                .setInitialTerminalSize(size);

        Terminal terminal = factory.createTerminal();
        this.screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);
    }

    public LanternaGUI(Screen screen) {
        this.screen = screen;
    }

    @Override
    public ACTION getNextAction() throws IOException {
        KeyStroke key = screen.pollInput();
        if (key == null) return ACTION.NONE;

        if (key.getKeyType() == KeyType.EOF) return ACTION.QUIT;
        if (key.getKeyType() == KeyType.Character && key.getCharacter() != null) {
            char c = Character.toLowerCase(key.getCharacter());
            return switch (c) {
                case 'q' -> ACTION.QUIT;
                case 'r' -> ACTION.ROTATE;
                case 't' -> ACTION.SHOW_BOARD;
                case 'a' -> ACTION.AUTO;
                case 'u' -> ACTION.UNDO;
                default -> ACTION.NONE;
            };
        }

        return switch (key.getKeyType()) {
            case ArrowUp -> ACTION.UP;
            case ArrowDown -> ACTION.DOWN;
            case ArrowLeft -> ACTION.LEFT;
            case ArrowRight -> ACTION.RIGHT;
            case Enter -> ACTION.SELECT;
            default -> ACTION.NONE;
        };
    }

    @Override
    public int getWidth() {
        return screen.getTerminalSize().getColumns();
    }

    @Override
    public int getHeight() {
        return screen.getTerminalSize().getRows();
    }

    @Override
    public void drawText(Position p, String text, String fg) {
        TextGraphics g = screen.newTextGraphics();
        g.setForegroundColor(TextColor.Factory.fromString(fg));
        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.putString(p.getX(), p.getY(), text);
    }

    @Override
    public void drawCharacter(Position p, char c, String fg) {
        TextGraphics g = screen.newTextGraphics();
        g.setForegroundColor(TextColor.Factory.fromString(fg));
        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.putString(p.getX(), p.getY(), String.valueOf(c));
    }

    @Override
    public void fillBackground(String bgColor) {
        TextGraphics g = screen.newTextGraphics();
        g.setBackgroundColor(TextColor.Factory.fromString(bgColor));
        g.fill(' ');
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }
}
