package battleship.model;

public class Settings {

    public enum BotDifficulty { RANDOM, HUNT, PERFECT }

    private int width;
    private int height;

    private int selector = 0;
    private BotDifficulty difficulty;

    public Settings() {
        this.width = 10;
        this.height = 10;
        this.difficulty = BotDifficulty.RANDOM;
    }

    public Settings(int width, int height,  BotDifficulty difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
    }

    public int getWidth() { return width; }
    public void setWidth(int width) { if (width > 0) this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { if (height > 0) this.height = height; }
    public BotDifficulty getDifficulty() { return difficulty; }
    public void setDifficulty(BotDifficulty difficulty) { this.difficulty = difficulty; }
    public int getSelector() { return selector; }

    public void setSelector(int selector) {
        this.selector = ((selector % 3) + 3) % 3;
    }
}
