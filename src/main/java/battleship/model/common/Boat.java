package battleship.model.common;

import battleship.model.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Boat {
    protected String name;
    protected int size;
    protected List<Position> positions;
    private Set<Integer> hits = new HashSet<>();
    protected boolean horizontal;

    public Boat(String name, int size) {
        this.name = name;
        this.size = size;
        this.positions = new ArrayList<>();
        this.horizontal = true;
    }

    public String getName() { return name; }
    public int getSize() { return size; }
    public boolean isHorizontal() { return horizontal; }
    public List<Position> getPositions() { return positions; }
    public Set<Integer> getHits() { return hits; }
    public void setHorizontal(boolean horizontal) { this.horizontal = horizontal; }
    public void addPosition(Position p) { positions.add(p); }
    public void clearPositions() { positions.clear(); }
    public void rotate() { horizontal = !horizontal; }
    public boolean isSunk() { return hits.size() >= size; }

    public void registerHit(Position pos) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals(pos)) {
                hits.add(i);
                return;
            }
        }
    }


    public boolean isPartHit(int p) { return hits.contains(p); }
}

