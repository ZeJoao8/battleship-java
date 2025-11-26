package battleship.states;

import battleship.controller.Controller;
import battleship.controller.game.EndController;
import battleship.viewer.Viewer;
import battleship.viewer.game.EndViewer;

public class EndState extends State<Boolean> {
    public EndState(Boolean model) { super(model); }
    @Override protected Viewer<Boolean> getViewer() { return new EndViewer(getModel()); }
    @Override protected Controller<Boolean> getController() { return new EndController(getModel()); }
}
