public class Settings {
    private int width;
    private int height;

    public Settings() {
        this.width = 10;
        this.height = 10;
    }

    public Settings(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

}

