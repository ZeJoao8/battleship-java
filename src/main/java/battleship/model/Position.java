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
        if(y-1>0){return new Position(x, y-1);}
        else return new Position(x, 0);
    }

    public Position moveLeftBounded() {
        if(x-1>0){return new Position(x-1, y);}
        else{return new Position(0, y);}
    }

    public Position moveDownBounded(int maxY) {
        if(y+1<maxY){return new Position(x, y+1);}
        else{return new Position(x, maxY);}
    }

    public Position moveRightBounded(int maxX) {
        if(x+1<maxX){return new Position(x+1, y);}
        else{return new Position(maxX, y);}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
}
