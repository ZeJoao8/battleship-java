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

    /**
     * Executa apenas o controller e — só se o state não tiver mudado — desenha o viewer.
     * Isto evita que o viewer antigo seja desenhado depois de um controller trocar de state.
     */
    public void step(Game game, GUI gui) throws Exception {
        // guarda referência do state atual antes de executar lógica
        Object before = game.getState();

        // obter ação e executar controller
        GUI.ACTION action = gui.getNextAction();
        controller.step(game, action);

        // se o state mudou durante controller.step, não desenhar o viewer antigo
        if (before != game.getState()) return;

        // caso contrário desenhar
        viewer.draw(gui);
    }
}
