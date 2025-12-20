package battleship.viewer.game.common;

import battleship.gui.GUI;
import battleship.model.Position;
import battleship.model.game.common.Boat;
import battleship.viewer.game.ElementViewer;

public class BoatViewer extends ElementViewer<Boat> {

    private final Position cursor;
    private final boolean valid;

    public BoatViewer(Boat boat) {
        this(boat, null, true);
    }

    public BoatViewer(Boat boat, Position cursor, boolean valid) {
        super(boat);
        this.cursor = cursor;
        this.valid = valid;
    }

    @Override
    public void draw(GUI gui, Position base) {
        Boat boat = getModel();
        for (int i = 0; i < boat.getSize(); i++) {
            Position p = computePosition(boat, i);
            if (p == null) return;
            gui.drawCharacter(new Position(base.getX() + p.getX() * 2, base.getY() + p.getY()), getChar(boat, i), computeColor(boat, i));
        }
    }

    private Position computePosition(Boat boat, int i) {
        if (boat.isPlaced())
            return boat.getPositions().get(i);
        if (cursor == null) return null;
        return boat.isHorizontal() ? new Position(cursor.getX() + i, cursor.getY()) : new Position(cursor.getX(), cursor.getY() + i);
    }

    private String computeColor(Boat boat, int i) {
        if (!boat.isPlaced())
            return valid ? "#00FF9D" : "#FF4444";
        return (boat.isSunk() || boat.isPartHit(i)) ? "#FF4444" : "#C17F42";
    }

    private char getChar(Boat boat, int idx) {
        if (idx == 0) return boat.isHorizontal() ? '<' : '^';
        if (idx == boat.getSize() - 1) return boat.isHorizontal() ? '>' : 'v';
        return boat.isHorizontal() ? '-' : '|';
    }
}
