package battleship.viewer.game;

import battleship.gui.GUI;

public abstract class ElementViewer<T> {
    private final T model;

    public ElementViewer(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void draw(GUI gui, int offsetX, int offsetY);
}
