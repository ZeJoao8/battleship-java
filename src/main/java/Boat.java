import java.util.ArrayList;
import java.util.List;

public abstract class Boat {
    protected String name;
    protected int size;
    protected boolean horizontal;
    protected List<Position> positions;
    protected int hitsReceived;

    public Boat(Position ipos, boolean horizontal){
        this.horizontal = horizontal;
        this.positions = new ArrayList<>();
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
}

