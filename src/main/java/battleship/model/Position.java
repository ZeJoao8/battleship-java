package battleship.model;

public class Position {
    private int x;
    private int y;
    public Position(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public void moveUp()    { y = Math.max(0, y - 1); }
    public void moveLeft()  { x = Math.max(0, x - 1); }

    public void moveDown(int maxY) { y = Math.min(maxY, y + 1); }
    public void moveRight(int maxX){ x = Math.min(maxX, x + 1); }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        Position p = (Position) o;
        return this.x == p.x && this.y == p.y;
    }
}

