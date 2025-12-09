package battleship.states;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.viewer.Viewer;

public abstract class State<T> {
    private final T model;

    public State(T model) {
        this.model = model;
    }

    protected abstract Viewer<T> getViewer();
    protected abstract Controller<T> getController();
    public T getModel() { return model; }

    public void step(Game game, GUI gui) throws Exception {
        Object before = game.getState();
        GUI.ACTION action = gui.getNextAction();
        Controller<T> controller = getController();
        controller.step(game, action);
        if (before != game.getState()) return;
        Viewer<T> viewer = getViewer();
        viewer.draw(gui);
    }
}
