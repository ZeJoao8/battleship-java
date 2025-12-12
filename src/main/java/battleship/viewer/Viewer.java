package battleship.viewer;

import battleship.gui.GUI;

import java.io.IOException;

public abstract class Viewer<T> {
    private final T model;

    public Viewer(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public void draw(GUI gui) throws IOException {
        gui.clear();
        gui.fillBackground("#010326");
        drawElements(gui);
        gui.refresh();
    }

    public int centerX(GUI gui, String text) {
        return (gui.getWidth() - text.length()) / 2;
    }

    protected abstract void drawElements(GUI gui);
}