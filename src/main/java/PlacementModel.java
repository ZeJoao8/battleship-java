import java.util.List;

public class PlacementModel {
    private final Board board;
    private final List<Boat> fleet;

    private int cursorX = 0;
    private int cursorY = 0;
    private boolean horizontal = true;

    public PlacementModel(Board board, List<Boat> fleet) {
        this.board = board;
        this.fleet = fleet;
    }
    public Board getBoard() { return board; }
    public List<Boat> getFleet() { return fleet; }
    public int getCursorX() { return cursorX; }
    public int getCursorY() { return cursorY; }
    public boolean isHorizontal() { return horizontal; }

    public void moveUp() { cursorY--; }
    public void moveDown() { cursorY++; }
    public void moveLeft() { cursorX--; }
    public void moveRight() { cursorX++; }
    public void rotate() { horizontal = !horizontal; }

    public Boat getCurrentBoat() {
        for (Boat b : fleet) if (b.getPositions().isEmpty()) return b;
        return fleet.getLast();
    }

    public boolean allPlaced() {
        for (Boat b : fleet) if (b.getPositions().isEmpty()) return false;
        return true;
    }
}


