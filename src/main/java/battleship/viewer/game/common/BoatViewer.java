package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.common.Boat;
import battleship.viewer.game.ElementViewer;

public class BoatViewer extends ElementViewer<Boat> {

    private final Position cursor;
    private final boolean valid;

    public BoatViewer(Boat boat) {
        super(boat);
        this.cursor = null;
        this.valid = true;
    }

    public BoatViewer(Boat boat, Position cursor, boolean valid) {
        super(boat);
        this.cursor = cursor;
        this.valid = valid;
    }

    @Override
    public void draw(GUI gui, int ox, int oy) {
        Boat boat = getModel();
        for (int i = 0; i < boat.getSize(); i++) {
            Position p;
            boolean sunk=false;
            if(boat.isPlaced()){p=boat.getPositions().get(i); sunk = boat.isSunk();}
            else{
                if(cursor==null){return;}
                p=boat.isHorizontal() ? new Position(cursor.getX() + i, cursor.getY()) : new Position(cursor.getX(), cursor.getY() + i);
            }
            String color = boat.isPlaced() ? ((sunk || boat.isPartHit(i)) ? "#FF4444" : "#C17F42") : (valid ? "#00FF9D" : "#FF4444");
            gui.drawCharacter(ox + p.getX() * 2, oy + p.getY(), getChar(boat, i), color);
        }
    }

    private char getChar(Boat boat, int idx) {
        if (idx == 0 && boat.isHorizontal()) return '<';
        if (idx == 0 && !boat.isHorizontal()) return '^';
        if (idx == boat.getSize() - 1 && boat.isHorizontal()) return '>';
        if (idx == boat.getSize() - 1 && !boat.isHorizontal()) return 'v';
        return boat.isHorizontal() ? '-' : '|';
    }
}
