package battleship.model.game.common;

import battleship.model.Position;
import battleship.model.Settings;

import java.util.List;

public class Board {

    private final int height;
    private final int width;
    private final CellState[][] grid;
    private final List<Boat> fleet;
    private final BoardPlacementService placementService = new BoardPlacementService();

    public Board(Settings settings) {
        this.width = settings.getWidth();
        this.height = settings.getHeight();
        this.grid = new CellState[width][height];
        initializeGrid();
        this.fleet = FleetFactory.standardFleet();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = CellState.WATER;
            }
        }
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }

    public List<Boat> getFleet() { return fleet; }

    public CellState getState(Position p) {
        return grid[p.getX()][p.getY()];
    }

    public boolean isInside(Position p) {
        return p.getX() >= 0 && p.getX() < width &&
                p.getY() >= 0 && p.getY() < height;
    }

    public boolean isWater(Position p) { return getState(p) == CellState.WATER; }
    public boolean isShip(Position p)  { return getState(p) == CellState.SHIP; }
    public boolean isHit(Position p)   { return getState(p) == CellState.HIT; }
    public boolean isMiss(Position p)  { return getState(p) == CellState.MISS; }

    public void markShip(Position p) {
        grid[p.getX()][p.getY()] = CellState.SHIP;
    }

    public void markHit(Position p) {
        grid[p.getX()][p.getY()] = CellState.HIT;

        for (int i = 0; i < fleet.size(); i++) {
            Boat b = fleet.get(i);
            for (int j = 0; j < b.getPositions().size(); j++) {
                if (b.getPositions().get(j).equals(p)) {
                    b.registerHit(p);
                    return;
                }
            }
        }
    }

    public void markMiss(Position p) {
        grid[p.getX()][p.getY()] = CellState.MISS;
    }

    public void markWater(Position p) {
        grid[p.getX()][p.getY()] = CellState.WATER;
    }

    public boolean canPlaceBoat(Boat boat, Position start, boolean horizontal) {
        return placementService.canPlace(this, boat, start, horizontal);
    }

    public void placeBoat(Boat boat, Position start, boolean horizontal) {
        placementService.place(this, boat, start, horizontal);
    }

    public void placeFleetRandomly() {
        placementService.placeFleetRandomly(this);
    }

    public boolean isValidBotTarget(Position p) {
        CellState cs = getState(p);
        return cs == CellState.WATER || cs == CellState.SHIP;
    }

    public boolean hasLost() {
        for (int i = 0; i < fleet.size(); i++) {
            if (!fleet.get(i).isSunk()) return false;
        }
        return true;
    }

    public Boat getNextUnplacedBoat() {
        for (int i = 0; i < fleet.size(); i++) {
            Boat b = fleet.get(i);
            if (!b.isPlaced()) return b;
        }
        return null;
    }

    public boolean allBoatsPlaced() {
        for (int i = 0; i < fleet.size(); i++) {
            if (!fleet.get(i).isPlaced()) return false;
        }
        return true;
    }
}
