package battleship.states;

import battleship.controller.Controller;
import battleship.controller.MenuController;
import battleship.model.menu.Menu;
import battleship.viewer.Viewer;
import battleship.viewer.MenuViewer;

public class MenuState extends State<Menu> {
    public MenuState(Menu model) { super(model); }
    @Override protected Viewer<Menu> getViewer() { return new MenuViewer(getModel()); }
    @Override protected Controller<Menu> getController() { return new MenuController(getModel()); }
}
