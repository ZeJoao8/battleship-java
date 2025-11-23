package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.common.Boat;

public class BoatViewer {

    public static void drawBoatGUI(Boat boat, int ox, int oy, GUI gui) {
        boolean sunk = boat.isSunk();
        for (int i = 0; i < boat.getPositions().size(); i++) {
            Position part = boat.getPositions().get(i);
            String color;
            if (sunk) color = "#FF4444";
            else if (boat.isPartHit(i)) color = "#FF4444";
            else color = "#C17F42";
            gui.drawCharacter(ox + part.getX() * 2, oy + part.getY(), getBoatChar(boat, i), color);
        }
    }

    public static void drawPreviewGUI(Boat boat, Position cursor, int ox, int oy, GUI gui, boolean valid) {
        String color = valid ? "#00FF9D" : "#FF4444";
        for (int i = 0; i < boat.getSize(); i++) {
            Position p = boat.isHorizontal() ? new Position(cursor.getX() + i, cursor.getY()) : new Position(cursor.getX(), cursor.getY() + i);
            gui.drawCharacter(ox + p.getX() * 2, oy + p.getY(), getBoatChar(boat, i), color);
        }
    }


    private static char getBoatChar(Boat boat, int idx) {
        if (idx == 0 && boat.isHorizontal()) return '<';
        if (idx == 0 && !boat.isHorizontal()) return '^';
        if (idx == boat.getSize() - 1 && boat.isHorizontal()) return '>';
        if (idx == boat.getSize() - 1 && !boat.isHorizontal()) return 'v';
        return boat.isHorizontal() ? '-' : '|';
    }
}
