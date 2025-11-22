package battleship;

import battleship.gui.GUI;
import battleship.gui.LanternaGUI;
import battleship.model.menu.Menu;
import battleship.model.settings.Settings;
import battleship.states.MenuState;
import battleship.states.State;

public class Game {
    private final GUI gui;
    private State<?> state;

    // settings globais partilhadas pelo jogo
    private final Settings settings;

    public Game() throws Exception {
        gui = new LanternaGUI(130, 42);
        settings = new Settings(); // criado uma vez e partilhado
        state = new MenuState(new Menu());
    }

    public void setState(State<?> newState) { this.state = newState; }

    public State<?> getState() { return state; }

    public Settings getSettings() { return settings; }

    public void start() throws Exception {
        while (state != null) {
            state.step(this, gui);
            Thread.sleep(10); // para não usar 100% CPU
        }
        gui.close();
    }
}
