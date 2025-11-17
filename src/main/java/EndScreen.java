import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class EndScreen {
    private final Screen screen;
    private final TextGraphics g;

    public EndScreen(Screen screen) {
        this.screen = screen;
        this.g = screen.newTextGraphics();
    }
    public void showEndMessage(boolean won) throws Exception{
        g.setBackgroundColor(TextColor.Factory.fromString("#010326"));
        g.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        g.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');
        String s1,s2,s3,s4;
        if(won){
            s1 = "▗▖  ▗▖▗▖▗▄▄▄▖▗▄▄▄▖▗▄▄▄▄▖▗▄▄▖ ▄   ▄";
            s2 = "▐▌  ▐▌▐▌▐▌     █  ▐▌  ▐▌▐▌ ▐▌ ▀▄▀ ";
            s3 = "▐▌  ▐▌▐▌▐▌     █  ▐▌  ▐▌▐▛▀▚▖  █";
            s4 = " ▝▚▞▘ ▐▌▐▙▄▄▖  █  ▐▙▄▄▟▌▐▌ ▐▌  █";
        }
        else{
            s1 = "▗▄▄▖ ▗▄▄▄▖▗▄▄▄▖▗▄▄▄▖ ▗▄▖ ▗▄▄▄▖";
            s2 = "▐▌ ▐▌▐▌   ▐▌   ▐▌   ▐▌ ▐▌  █";
            s3 = "▐▌ ▐▌▐▛▀  ▐▛▀▀▘▐▛▀  ▐▛▀▜▌  █";
            s4 = "▐▙▄▞▘▐▙▄▄▖▐▌   ▐▙▄▄▖▐▌ ▐▌  █";
        }
        g.putString(10, 6,s1);
        g.putString(10, 7,s2);
        g.putString(10, 8,s3);
        g.putString(10, 9,s4);
        g.putString(10, 10,"Pressiona Q para voltar ao menu");

        screen.refresh();
        while(true) {
            KeyStroke key = screen.readInput();
            if (key!= null && key.getKeyType() == KeyType.Character) {
                char c= key.getCharacter();
                if (c == 'q' || c == 'Q') return;
            }
        }
    }
}
