import java.util.ArrayList;
import java.util.List;


public abstract class Boat {
    protected String name;
    protected int size;
    protected List<Position> positions;
    protected int hitsReceived;
    protected boolean horizontal;

    public Boat(String name, int size){
        this.name = name;
        this.size = size;
        this.positions = new ArrayList<>();
        this.hitsReceived = 0;
        this.horizontal = true;
    }

    public String getName() {return name;}
    public int getSize() {return size;}
    public boolean isHorizontal() {return horizontal;}
    public List<Position> getPositions() {return positions;}
    public int getHitsReceived() {return hitsReceived;}

    public void setName(String name) {this.name = name;}
    public void setSize(int size) {this.size = size;}
    public void setHorizontal(boolean horizontal) {this.horizontal = horizontal;}
    public void setPositions(List<Position> positions) {this.positions = positions;}
    public void setHitsReceived(int hitsReceived) {this.hitsReceived = hitsReceived;}

    public void addPosition(Position p) {positions.add(p);}
    public void registerHit() {hitsReceived++;}
    public boolean isSunk() {return hitsReceived >= size;}

    public void clearPositions() {
        positions.clear();
    }

}

