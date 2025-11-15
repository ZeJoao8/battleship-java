import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class MenuView {
    private final Selector selector;
    private final String[] options = {"Play", "Settings", "Quit"};

    public MenuView() {
        selector = new Selector(options.length);
    }

    public Selector getSelector() {
        return selector;
    }

    public void drawMenu(Screen screen) throws IOException {
        TextGraphics g = screen.newTextGraphics();

        // Fundo
        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        g.fill(' ');

        // Título
        String s1 = "▗▄▄▖  ▗▄▖▗▄▄▄▖▗▄▖ ▗▖   ▗▖ ▗▖ ▗▄▖     ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▖ ▗▖   ";
        String s2 = "▐▌ ▐▌▐▌ ▐▌ █ ▐▌ ▐▌▐▌   ▐▌ ▐▌▐▌ ▐▌    ▐▛▚▖▐▌▐▌ ▐▌▐▌  ▐▌▐▌ ▐▌▐▌   ";
        String s3 = "▐▛▀▚▖▐▛▀▜▌ █ ▐▛▀▜▌▐▌   ▐▛▀▜▌▐▛▀▜▌    ▐▌ ▝▜▌▐▛▀▜▌▐▌  ▐▌▐▛▀▜▌▐▌   ";
        String s4 = "▐▌▄▞▘▐▌ ▐▌ █ ▐▌ ▐▌▐▙▄▄▖▐▌ ▐▌▐▌ ▐▌    ▐▌  ▐▌▐▌ ▐▌ ▝▚▞▘ ▐▌ ▐▌▐▙▄▄▖";

        int titleCol = (screen.getTerminalSize().getColumns() - s1.length()) / 2;
        g.putString(titleCol, 4, s1);
        g.putString(titleCol, 5, s2);
        g.putString(titleCol, 6, s3);
        g.putString(titleCol, 7, s4);

        // Opções
        int startRow = 15;
        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int col = (screen.getTerminalSize().getColumns() - text.length()) / 2;
            if (selector.getSelectedIndex() == i) {
                g.setForegroundColor(TextColor.Factory.fromString("#00FF9D")); // verde
                g.putString(col - 2, startRow + i * 2, "> " + text + " <");
            } else {
                g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                g.putString(col, startRow + i * 2, text);
            }
        }

        screen.refresh();
    }
}


