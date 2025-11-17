package BattleShip.Controller;

import BattleShip.Model.Settings;
import BattleShip.View.MenuView;
import BattleShip.View.SettingsView;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;

public class Menu {

    enum State {MENU, GAME, SETTINGS, EXIT}

    private Settings settings;
    private SettingsView settingsView;
    private MenuView menuView;
    private MenuController menuController;

    public Menu() {
        menuView = new MenuView();
        menuController = new MenuController(menuView);
    }

    public void start() {
        try {
            DefaultTerminalFactory factory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(130, 42));

            Screen screen = factory.createScreen();
            screen.startScreen();

            State state = State.MENU;
            settings = new Settings();
            settingsView = new SettingsView(settings);

            while (state != State.EXIT) {
                switch (state) {
                    case MENU -> {
                        menuView.drawMenu(screen);
                        KeyStroke key = screen.readInput();
                        state = menuController.handleInput(key);
                    }
                    case GAME -> {
                        Game game = new Game(screen, settings);
                        game.runGame();
                        state = State.MENU;
                    }
                    case SETTINGS -> {
                        settingsView.runSettings(screen);
                        state = State.MENU;
                    }
                }
            }

            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


