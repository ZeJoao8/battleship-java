package battleship.model.game;

public class GameStats {

    private int playerShots = 0;
    private int playerHits = 0;
    private int botShots = 0;
    private int botHits = 0;
    private final long startTime;
    private long endTime = 0;
    private BattleModel finalModel;

    public GameStats() {
        this.startTime = System.currentTimeMillis();
    }

    public void registerPlayerShot(boolean hit) {
        playerShots++;
        if (hit) playerHits++;
    }

    public void registerBotShot(boolean hit) {
        botShots++;
        if (hit) botHits++;
    }

    public void finish() {
        this.endTime = System.currentTimeMillis();
    }

    public void setFinalModel(BattleModel model) {
        this.finalModel = model;
    }

    public BattleModel getFinalModel() {
        return finalModel;
    }

    public int getPlayerShots() { return playerShots; }
    public int getPlayerHits() { return playerHits; }
    public int getBotShots() { return botShots; }
    public int getBotHits() { return botHits; }

    public double getPlayerAccuracy() {
        return playerShots == 0 ? 0 : (100.0 * playerHits / playerShots);
    }

    public double getBotAccuracy() {
        return botShots == 0 ? 0 : (100.0 * botHits / botShots);
    }

    public long getDurationSeconds() {
        return (endTime - startTime) / 1000;
    }
}
