public class PortaAvioes extends Boat {
    public PortaAvioes(Position ipos, boolean horizontal) {
        super(ipos, horizontal);
        this.name = "Porta Aviões";
        this.size = 5;
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

