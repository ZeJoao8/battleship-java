package battleship.controller;

import battleship.gui.GUI;
import battleship.Game;

public abstract class Controller<T> {
    private final T model;

    public Controller(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void step(Game game, GUI.ACTION action) throws Exception;
}
