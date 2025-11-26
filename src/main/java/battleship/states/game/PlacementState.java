package battleship.states.game;

import battleship.controller.Controller;
import battleship.controller.game.PlacementController;
import battleship.model.game.PlacementModel;
import battleship.model.Settings;
import battleship.states.State;
import battleship.viewer.Viewer;
import battleship.viewer.game.placement.PlacementViewer;

public class PlacementState extends State<PlacementModel> {
    private final Settings settings;

    public PlacementState(PlacementModel model, Settings settings) {
        super(model);
        this.settings = settings;
    }

    @Override
    protected Viewer<PlacementModel> getViewer() {
        return new PlacementViewer(getModel());
    }

    @Override
    protected Controller<PlacementModel> getController() {
        return new PlacementController(getModel(), settings);
    }

    public Settings getSettings() {
        return settings;
    }
}
