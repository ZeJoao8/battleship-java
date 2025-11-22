package battleship.model.common;

import battleship.model.Position;
import battleship.model.common.boats.*;
import battleship.model.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int Water = 0;
    public static final int Ship  = 1;
    public static final int Hit   = 2;
    public static final int Miss  = 3;

    private final int height;
    private final int width;
    private final int[][] board;
    private final List<Boat> fleet;

    public Board(Settings settings) {
        this.height = settings.getHeight();
        this.width  = settings.getWidth();
        this.board  = new int[width][height];
        this.fleet  = new ArrayList<>();
        setFleet();
    }

    public int getHeigth() { return height; }
    public int getWidth()  { return width; }
    public List<Boat> getFleet() { return fleet; }

    private void setFleet() {
        fleet.add(new PortaAvioes());
        fleet.add(new NavioTanque());
        fleet.add(new ContraTorpedeiro());
        fleet.add(new ContraTorpedeiro());
        fleet.add(new Submarino());
    }

    public boolean inside(Position p) {
        return p.getX() >= 0 && p.getX() < width &&
                p.getY() >= 0 && p.getY() < height;
    }

    public int getCell(Position p) {
        return board[p.getX()][p.getY()];
    }

    public void setCell(Position p, int value) {
        board[p.getX()][p.getY()] = value;
    }

    public boolean canPlaceBoat(Boat boat, Position start, boolean horizontal) {
        int size = boat.getSize();

        for (int i = 0; i < size; i++) {
            Position p = horizontal ? new Position(start.getX() + i, start.getY()) : new Position(start.getX(), start.getY() + i);
            if (!inside(p)) return false;
            if (getCell(p) != Water) return false;
        }
        return true;
    }

    public void placeBoat(Boat boat, Position start, boolean horizontal) {
        if (!canPlaceBoat(boat, start, horizontal)) return;
        boat.clearPositions();
        boat.setHorizontal(horizontal);
        int size = boat.getSize();

        for (int i = 0; i < size; i++) {
            Position p = horizontal ? new Position(start.getX() + i, start.getY()) : new Position(start.getX(), start.getY() + i);
            setCell(p, Ship);
            boat.addPosition(p);
        }
    }

    public void placeFleetRandomly() {
        Random rnd = new Random();

        for (Boat boat : fleet) {
            boolean placed = false;
            while (!placed) {
                boolean h = rnd.nextBoolean();
                Position p = new Position(rnd.nextInt(width), rnd.nextInt(height));

                if (canPlaceBoat(boat, p, h)) {
                    placeBoat(boat, p, h);
                    placed = true;
                }
            }
        }
    }

    public void markHit(Position p) {
        setCell(p, Hit);

        for (Boat b : fleet) {
            for (Position part : b.getPositions()) {
                if (part.equals(p)) {
                    b.registerHit(p.getX(), p.getY());
                    return;
                }
            }
        }
    }

    public void markMiss(Position p) {
        setCell(p, Miss);
    }

    public boolean isValidBotTarget(Position p) {
        int cell = getCell(p);
        return cell == Water || cell == Ship;
    }

    public boolean hasLost() {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (board[x][y] == Ship)
                    return false;

        return true;
    }
}
