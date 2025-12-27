package battleship.states.game;

import battleship.controller.Controller;
import battleship.controller.game.EndController;
import battleship.model.game.GameStats;
import battleship.states.State;
import battleship.viewer.Viewer;
import battleship.viewer.game.EndViewer;

public class EndState extends State<Boolean> {

    private final GameStats stats;
    private boolean showBoards = false;

    public EndState(Boolean model, GameStats stats) {
        super(model);
        this.stats = stats;
    }

    public void toggleBoards() {
        this.showBoards = !showBoards;
    }

    public boolean isShowingBoards() {
        return showBoards;
    }

    @Override
    public Viewer<Boolean> getViewer() {
        return new EndViewer(getModel(), stats, this);
    }

    @Override
    public Controller<Boolean> getController() {
        return new EndController(getModel(), this);
    }
}
