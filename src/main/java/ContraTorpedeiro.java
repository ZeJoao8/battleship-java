public class ContraTorpedeiro extends Boat {
    public ContraTorpedeiro(Position ipos, boolean horizontal) {
        super(ipos, horizontal);
        this.name = "ContraTorpedeiro";
        this.size = 3;
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
