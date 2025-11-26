package battleship.states.game;

import battleship.controller.Controller;
import battleship.controller.game.EndController;
import battleship.states.State;
import battleship.viewer.Viewer;
import battleship.viewer.game.EndViewer;

public class EndState extends State<Boolean> {
    public EndState(Boolean model) { super(model); }
    @Override
    public Viewer<Boolean> getViewer() { return new EndViewer(getModel()); }
    @Override
    public Controller<Boolean> getController() { return new EndController(getModel()); }
}
