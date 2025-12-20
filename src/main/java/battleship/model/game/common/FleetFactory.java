package battleship.model.game.common;

import battleship.model.game.common.boats.*;

import java.util.ArrayList;
import java.util.List;

public final class FleetFactory {

    private FleetFactory() {}

    public static List<Boat> standardFleet() {
        List<Boat> fleet = new ArrayList<>();
        fleet.add(new Carrier());
        fleet.add(new Battleship());
        fleet.add(new Cruiser());
        fleet.add(new Cruiser());
        fleet.add(new Destroyer());
        return fleet;
    }
}
