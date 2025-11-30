package battleship.states;

import battleship.Game;
import battleship.controller.Controller;
import battleship.gui.GUI;
import battleship.viewer.Viewer;

public abstract class State<T> {
    private final T model;
    private final Viewer<T> viewer;
    private final Controller<T> controller;

    public State(T model) {
        this.model = model;
        this.viewer = getViewer();
        this.controller = getController();
    }

    protected abstract Viewer<T> getViewer();
    protected abstract Controller<T> getController();

    public T getModel() { return model; }

    public void step(Game game, GUI gui) throws Exception {
        Object before = game.getState();
        GUI.ACTION action = gui.getNextAction();
        controller.step(game, action);
        if (before != game.getState()) return;
        viewer.draw(gui);
    }
}
