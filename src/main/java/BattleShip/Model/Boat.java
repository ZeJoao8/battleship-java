package BattleShip.Model;

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

    public Boat(String name, int size){
        this.name = name;
        this.size = size;
        this.positions = new ArrayList<>();
        this.horizontal = true;
    }

    public String getName() {return name;}
    public int getSize() {return size;}
    public boolean isHorizontal() {return horizontal;}
    public List<Position> getPositions() {return positions;}
    public Set<Integer> getHits() {return hits;}

    public void setName(String name) {this.name = name;}
    public void setSize(int size) {this.size = size;}
    public void setHorizontal(boolean horizontal) {this.horizontal = horizontal;}
    public void setPositions(List<Position> positions) {this.positions = positions;}

    public void addPosition(Position p) {positions.add(p);}

    public boolean isSunk() {return hits.size() >= size;}

    public void clearPositions() {
        positions.clear();
    }

    public void rotate(){horizontal = !horizontal;}

    public void registerHit(int x, int y) {
        for (int i = 0; i < positions.size(); i++) {
            Position p = positions.get(i);
            if (p.getX() == x && p.getY() == y) {
                hits.add(i);
                return;
            }
        }
    }
    public boolean isPartHit(int p) {
        return hits.contains(p);
    }

}

