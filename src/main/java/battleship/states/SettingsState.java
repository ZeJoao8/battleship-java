package battleship.states;

import battleship.controller.Controller;
import battleship.model.settings.Settings;
import battleship.viewer.Viewer;
import battleship.viewer.settings.SettingsViewer;
import battleship.controller.settings.SettingsController;

public class SettingsState extends State<Settings> {

    public SettingsState(Settings model) { super(model); }

    @Override
    protected Viewer<Settings> getViewer() {
        return new SettingsViewer(getModel());
    }

    @Override
    protected Controller<Settings> getController() {
        return new SettingsController(getModel());
    }
}
