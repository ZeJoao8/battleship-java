package battleship.viewer.common;

import battleship.gui.GUI;
import battleship.model.common.Boat;
import battleship.model.Position;

public class BoatViewer {

    public static void drawBoatGUI(Boat boat, int offsetX, int offsetY, GUI gui) {
        for (int i = 0; i < boat.getPositions().size(); i++) {
            Position pos = boat.getPositions().get(i);
            char ch = getBoatChar(boat, i);
            String color = boat.isPartHit(i) ? "#FF4444" : "#C17F42";
            gui.drawCharacter(offsetX + pos.getX() * 2, offsetY + pos.getY(), ch, color);
        }
    }

    public static void drawSunkBoatGUI(Boat boat, int offsetX, int offsetY, GUI gui) {
        for (int i = 0; i < boat.getPositions().size(); i++) {
            Position pos = boat.getPositions().get(i);
            char ch = getBoatChar(boat, i);
            gui.drawCharacter(offsetX + pos.getX() * 2, offsetY + pos.getY(), ch, "#FF4444");
        }
    }

    public static void drawPreviewGUI(Boat boat, int cursorX, int cursorY, int ox, int oy, GUI gui, boolean valid) {
        String color = valid ? "#00FF9D" : "#FF4444"; // verde ou vermelho

        for (int i = 0; i < boat.getSize(); i++) {
            int x = cursorX + (boat.isHorizontal() ? i : 0);
            int y = cursorY + (boat.isHorizontal() ? 0 : i);
            gui.drawCharacter(ox + x * 2, oy + y, getBoatChar(boat, i), color);
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
