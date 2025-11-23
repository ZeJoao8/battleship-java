package battleship.model.common;

import battleship.model.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Boat {
    protected String name;
    protected int size;
    protected List<Position> positions;
    protected boolean[] hits;
    protected boolean horizontal;

    public Boat(String name, int size) {
        this.name = name;
        this.size = size;
        this.positions = new ArrayList<>();
        this.hits = new boolean[size];
        this.horizontal = true;
    }

    public String getName() { return name; }
    public int getSize() { return size; }
    public boolean isHorizontal() { return horizontal; }
    public List<Position> getPositions() { return positions; }

    public void setHorizontal(boolean h) {
        this.horizontal = h;
    }

    public void clearPositions() {
        positions.clear();
        hits = new boolean[size];
    }

    public void addPosition(Position p) {
        positions.add(p);
    }

    public void rotate() {
        horizontal = !horizontal;
    }

    public void registerHit(Position pos) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals(pos)) {
                hits[i] = true;
                return;
            }
        }
    }

    public boolean isPartHit(int index) {
        return hits[index];
    }

    public boolean isSunk() {
        for (boolean h : hits)
            if (!h) return false;
        return true;
    }
}
