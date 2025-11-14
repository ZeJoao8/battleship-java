public class NavioTanque extends Boat {
    public NavioTanque(Position ipos, boolean horizontal) {
        super(ipos, horizontal);
        this.name = "Navio Tanque";
        this.size = 4;
        this.hitsReceived = 0;
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                addPosition(new Position(ipos.getX() + i, ipos.getY()));
            } else {
                addPosition(new Position(ipos.getX(), ipos.getY() + i));
            }
        }
    }
}
