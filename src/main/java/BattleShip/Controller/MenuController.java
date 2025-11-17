package BattleShip.Controller;

import BattleShip.View.MenuView;
import BattleShip.util.Selector;
import com.googlecode.lanterna.input.KeyStroke;

public class MenuController {
    private final Selector selector;

    public MenuController(MenuView view) {
        this.selector = view.getSelector();
    }

    public Menu.State handleInput(KeyStroke key) {
        if (key == null) return Menu.State.MENU;

        switch (key.getKeyType()) {
            case ArrowDown -> selector.next();
            case ArrowUp -> selector.previous();
            case Enter -> {
                return switch (selector.getSelectedIndex()) {
                    case 0 -> Menu.State.GAME;
                    case 1 -> Menu.State.SETTINGS;
                    default -> Menu.State.EXIT;
                };
            }
            case Escape, EOF -> {
                return Menu.State.EXIT;
            }
            case Character -> {
                if (Character.toLowerCase(key.getCharacter()) == 'q') return Menu.State.EXIT;
            }
        }

        return Menu.State.MENU;
    }
}


