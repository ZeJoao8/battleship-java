package battleship.gui;

import battleship.model.Position;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LanternaGUITest {

    private Screen screen;
    private LanternaGUI gui;
    private TextGraphics tg;
    private TerminalSize size;

    @BeforeEach
    void setup() {
        screen = mock(Screen.class);
        tg = mock(TextGraphics.class);
        size = mock(TerminalSize.class);

        when(screen.newTextGraphics()).thenReturn(tg);
        when(screen.getTerminalSize()).thenReturn(size);

        gui = new LanternaGUI(screen);
    }

    @Test void keyNullReturnsNone() throws IOException {
        when(screen.pollInput()).thenReturn(null);
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    @Test void keyEOFReturnsQuit() throws IOException {
        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.EOF));
        assertEquals(GUI.ACTION.QUIT, gui.getNextAction());
    }

    @Test void keyQReturnsQuit() throws IOException {
        when(screen.pollInput()).thenReturn(new KeyStroke('q',false,false));
        assertEquals(GUI.ACTION.QUIT, gui.getNextAction());
    }

    @Test void keyRReturnsRotate() throws IOException {
        when(screen.pollInput()).thenReturn(new KeyStroke('r',false,false));
        assertEquals(GUI.ACTION.ROTATE, gui.getNextAction());
    }

    @Test void keyCharacterOtherReturnsNone() throws IOException {
        when(screen.pollInput()).thenReturn(new KeyStroke('x',false,false));
        assertEquals(GUI.ACTION.NONE, gui.getNextAction());
    }

    @Test void keyArrowsAndEnter() throws IOException {
        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        assertEquals(GUI.ACTION.UP, gui.getNextAction());

        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        assertEquals(GUI.ACTION.DOWN, gui.getNextAction());

        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        assertEquals(GUI.ACTION.LEFT, gui.getNextAction());

        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowRight));
        assertEquals(GUI.ACTION.RIGHT, gui.getNextAction());

        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(GUI.ACTION.SELECT, gui.getNextAction());
    }

    @Test void getWidthAndHeight() {
        when(size.getColumns()).thenReturn(80);
        when(size.getRows()).thenReturn(30);

        assertEquals(80, gui.getWidth());
        assertEquals(30, gui.getHeight());
    }

    @Test void drawText() {
        gui.drawText(new Position(2,5),"OLA","#11AA22");

        verify(tg).setForegroundColor(TextColor.Factory.fromString("#11AA22"));
        verify(tg).setBackgroundColor(TextColor.Factory.fromString("#010326"));
        verify(tg).putString(2,5,"OLA");
    }

    @Test void drawCharacter() {
        gui.drawCharacter(new Position(10,3),'@',"#AA22EE");

        verify(tg).setForegroundColor(TextColor.Factory.fromString("#AA22EE"));
        verify(tg).setBackgroundColor(TextColor.Factory.fromString("#010326"));
        verify(tg).putString(10,3,"@");
    }

    @Test void fillBackgroundColor() {
        gui.fillBackground("#111111");

        verify(tg).setBackgroundColor(TextColor.Factory.fromString("#111111"));
        verify(tg).fill(' ');
    }

    @Test void clearScreen() {
        gui.clear();
        verify(screen).clear();
    }

    @Test void refreshScreen() throws IOException {
        gui.refresh();
        verify(screen).refresh();
    }

    @Test void closeScreen() throws IOException {
        gui.close();
        verify(screen).close();
    }
}
