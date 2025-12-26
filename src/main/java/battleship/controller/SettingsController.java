package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.Menu;
import battleship.model.Settings;
import battleship.states.MenuState;

public class SettingsController extends Controller<Settings> {

    public SettingsController(Settings model) { super(model); }

    @Override
    public void step(Game game, GUI.ACTION action) {
        Settings s = getModel();
        switch (action) {
            case UP -> s.setSelector(s.getSelector() - 1);
            case DOWN -> s.setSelector(s.getSelector() + 1);
            case LEFT -> {
                if (s.getSelector() == 0 && s.getWidth() > 6)
                    s.setWidth(s.getWidth() - 1);
                if (s.getSelector() == 1 && s.getHeight() > 6)
                    s.setHeight(s.getHeight() - 1);
                if (s.getSelector() == 2)
                    changeDifficulty(s, -1);
            }
            case RIGHT -> {
                if (s.getSelector() == 0 && s.getWidth() < 14)
                    s.setWidth(s.getWidth() + 1);
                if (s.getSelector() == 1 && s.getHeight() < 14)
                    s.setHeight(s.getHeight() + 1);
                if (s.getSelector() == 2)
                    changeDifficulty(s, +1);
            }
            case QUIT, SELECT -> game.setState(new MenuState(new Menu()));
        }
    }

    private void changeDifficulty(Settings s, int dir) {
        Settings.BotDifficulty[] list = Settings.BotDifficulty.values();
        int i = s.getDifficulty().ordinal();
        s.setDifficulty(list[(i + dir + list.length) % list.length]);
    }
}
