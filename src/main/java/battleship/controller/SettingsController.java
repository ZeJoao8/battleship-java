package battleship.controller;

import battleship.Game;
import battleship.gui.GUI;
import battleship.model.menu.Menu;
import battleship.model.settings.Settings;
import battleship.states.MenuState;

public class SettingsController extends Controller<Settings> {

    public SettingsController(Settings model) {
        super(model);
    }

    @Override
    public void step(Game game, GUI.ACTION action) {
        Settings settings = getModel();

        switch (action) {
            case UP -> settings.setSelector(settings.getSelector() - 1);
            case DOWN -> settings.setSelector(settings.getSelector() + 1);

            case LEFT -> {
                if (settings.getSelector() == 0 && settings.getWidth() > 6)
                    settings.setWidth(settings.getWidth() - 1);
                if (settings.getSelector() == 1 && settings.getHeight() > 6)
                    settings.setHeight(settings.getHeight() - 1);
            }

            case RIGHT -> {
                if (settings.getSelector() == 0 && settings.getWidth() < 14)
                    settings.setWidth(settings.getWidth() + 1);
                if (settings.getSelector() == 1 && settings.getHeight() < 14)
                    settings.setHeight(settings.getHeight() + 1);
            }

            case QUIT, SELECT -> game.setState(new MenuState(new Menu()));
            default -> { }
        }
    }
}
