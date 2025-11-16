import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class BoatView {

    public static void drawBoat(Boat boat, int offsetX, int offsetY, TextGraphics g) {
        for (int i = 0; i < boat.getPositions().size(); i++) {
            Position pos = boat.getPositions().get(i);
            char ch = getBoatChar(boat, i);
            g.putString(offsetX + pos.getX() * 2, offsetY + pos.getY(), String.valueOf(ch));
        }
    }

    public static void drawPreview(Boat boat, int cursorX, int cursorY, int offsetX, int offsetY, TextGraphics g) {
        for (int i = 0; i < boat.getSize(); i++) {
            int bx = cursorX + (boat.isHorizontal() ? i : 0);
            int by = cursorY + (boat.isHorizontal() ? 0 : i);
            g.setForegroundColor(TextColor.Factory.fromString("#00FF9D"));
            g.putString(offsetX + bx * 2, offsetY + by, "#");
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


