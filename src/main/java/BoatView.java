import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class BoatView {

    public static void drawBoat(Boat boat, int offsetX, int offsetY, TextGraphics g) {
        for (int i = 0; i < boat.getPositions().size(); i++) {
            Position pos = boat.getPositions().get(i);
            char ch = getBoatChar(boat, i);
            if (boat.isPartHit(i)) {
                g.setForegroundColor(TextColor.Factory.fromString("#FF4444")); // vermelho
            } else {
                g.setForegroundColor(TextColor.Factory.fromString("#C17F42")); // castanho
            }
            g.putString(offsetX + pos.getX() * 2, offsetY + pos.getY(), String.valueOf(ch));
        }
    }

    public static void drawSunkBoat(Boat boat, int offsetX, int offsetY, TextGraphics g) {
        for (int i = 0; i < boat.getPositions().size(); i++) {
            Position pos = boat.getPositions().get(i);
            char ch = getBoatChar(boat, i); // já considera horizontal ou vertical

            g.setForegroundColor(TextColor.Factory.fromString("#FF4444")); // vermelho forte
            g.putString(offsetX + pos.getX() * 2, offsetY + pos.getY(), String.valueOf(ch));
        }
    }

    public static void drawPreview(Boat boat, int cursorX, int cursorY, int offsetX, int offsetY, TextGraphics g) {
        for (int i = 0; i < boat.getSize(); i++) {
            int bx = cursorX + (boat.isHorizontal() ? i : 0);
            int by = cursorY + (boat.isHorizontal() ? 0 : i);
            g.setForegroundColor(TextColor.Factory.fromString("#00FF9D"));
            char ch = getBoatChar(boat, i);
            g.putString(offsetX + bx * 2, offsetY + by, String.valueOf(ch));
        }
    }

    private static char getBoatChar(Boat boat, int idx) {
        if(idx == 0 && boat.isHorizontal()) return '<';
        if(idx == 0 && !boat.isHorizontal()) return '^';
        if(idx == boat.getSize() - 1 && boat.isHorizontal()) return '>';
        if(idx == boat.getSize() - 1 && !boat.isHorizontal()) return 'v';
        return boat.isHorizontal() ? '-' : '|';
    }
}


