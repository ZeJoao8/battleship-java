package battleship.viewer.game;

import battleship.gui.GUI;
import battleship.model.Position;

public abstract class ElementViewer<T> {
    private final T model;

    public ElementViewer(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void draw(GUI gui, Position p);
}
