package battleship.model;

import java.util.Objects;

public final class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Position up()    { return new Position(x, y - 1); }
    public Position down()  { return new Position(x, y + 1); }
    public Position left()  { return new Position(x - 1, y); }
    public Position right() { return new Position(x + 1, y); }

    public Position moveUpBounded() {
        return new Position(x, Math.max(0, y - 1));
    }

    public Position moveLeftBounded() {
        return new Position(Math.max(0, x - 1), y);
    }

    public Position moveDownBounded(int maxY) {
        return new Position(x, Math.min(maxY, y + 1));
    }

    public Position moveRightBounded(int maxX) {
        return new Position(Math.min(maxX, x + 1), y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
}
