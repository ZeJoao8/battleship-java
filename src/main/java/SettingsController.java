public class SettingsController {
    private Settings settings;

    public SettingsController(Settings settings) {
        this.settings = settings;
    }

    public void increaseWidth() {
        if(settings.getWidth() < 14)
            settings.setWidth(settings.getWidth()+1);
    }
    public void decreaseWidth() {
        if(settings.getWidth() > 6)
            settings.setWidth(settings.getWidth() - 1);
    }

    public void increaseHeight() {
        if(settings.getHeight() < 14)
            settings.setHeight(settings.getHeight()+1);
    }
    public void decreaseHeight() {
        if(settings.getHeight() > 6)
            settings.setHeight(settings.getHeight()-1);
    }
}

