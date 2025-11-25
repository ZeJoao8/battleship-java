package battleship.model.common;

import battleship.model.Position;
import battleship.model.common.boats.*;
import battleship.model.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private final int height;
    private final int width;
    private final CellState[][] grid;
    private final List<Boat> fleet;

    public Board(Settings settings) {
        this.width = settings.getWidth();
        this.height = settings.getHeight();
        this.grid = new CellState[width][height];
        initializeGrid();
        this.fleet = new ArrayList<>();
        createFleet();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                grid[x][y] = CellState.WATER;
    }

    private void createFleet() {
        fleet.add(new PortaAvioes());
        fleet.add(new NavioTanque());
        fleet.add(new ContraTorpedeiro());
        fleet.add(new ContraTorpedeiro());
        fleet.add(new Submarino());
    }

    public int getWidth()  { return width; }
    public int getHeigth() { return height; }

    public List<Boat> getFleet() { return fleet; }

    public CellState getState(Position p) {
        return grid[p.getX()][p.getY()];
    }

    private void setState(Position p, CellState state) {
        grid[p.getX()][p.getY()] = state;
    }

    public boolean isInside(Position p) {
        return p.getX() >= 0 && p.getX() < width &&
                p.getY() >= 0 && p.getY() < height;
    }

    public boolean isWater(Position p) { return getState(p) == CellState.WATER; }
    public boolean isShip(Position p)  { return getState(p) == CellState.SHIP; }
    public boolean isHit(Position p)   { return getState(p) == CellState.HIT; }
    public boolean isMiss(Position p)  { return getState(p) == CellState.MISS; }

    public boolean canPlaceBoat(Boat boat, Position start, boolean horizontal) {
        int size = boat.getSize();

        for (int i = 0; i < size; i++) {
            Position p = horizontal ?
                    new Position(start.getX() + i, start.getY()) :
                    new Position(start.getX(), start.getY() + i);

            if (!isInside(p)) return false;
            if (!isWater(p)) return false;
        }
        return true;
    }

    public void placeBoat(Boat boat, Position start, boolean horizontal) {
        if (!canPlaceBoat(boat, start, horizontal)) return;
        for (Position old : boat.getPositions()) {setState(old, CellState.WATER);}
        boat.clearPositions();
        boat.setHorizontal(horizontal);
        for (int i = 0; i < boat.getSize(); i++) {
            Position p = horizontal ? new Position(start.getX() + i, start.getY()) : new Position(start.getX(), start.getY() + i);
            setState(p, CellState.SHIP);
            boat.addPosition(p);
        }
    }

    public void placeFleetRandomly() {
        Random rnd = new Random();

        for (Boat boat : fleet) {
            boolean placed = false;

            while (!placed) {
                boolean hor = rnd.nextBoolean();
                Position p = new Position(rnd.nextInt(width), rnd.nextInt(height));

                if (canPlaceBoat(boat, p, hor)) {
                    placeBoat(boat, p, hor);
                    placed = true;
                }
            }
        }
    }

    public void markHit(Position p) {
        setState(p, CellState.HIT);
        for (Boat b : fleet) {
            for (Position part : b.getPositions()) {
                if (part.equals(p)) {
                    b.registerHit(p);
                    return;
                }
            }
        }
    }

    public void markMiss(Position p) {
        setState(p, CellState.MISS);
    }

    public boolean isValidBotTarget(Position p) {
        CellState cs = getState(p);
        return cs == CellState.WATER || cs == CellState.SHIP;
    }

    public boolean hasLost() {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (grid[x][y] == CellState.SHIP) return false;
        return true;
    }

    public Boat getNextUnplacedBoat() {
        for (Boat b : fleet)
            if (!b.isPlaced())
                return b;
        return null;
    }

    public boolean allBoatsPlaced() {
        for (Boat b : fleet)
            if (!b.isPlaced())
                return false;
        return true;
    }
}
