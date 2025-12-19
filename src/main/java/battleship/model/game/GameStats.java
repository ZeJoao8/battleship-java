package battleship.model.game;

public class GameStats {

    private int playerShots = 0;
    private int playerHits = 0;
    private int botShots = 0;
    private int botHits = 0;
    private final GameTimer timer;
    private int finalSeconds = -1;
    private BattleModel finalModel;

    public GameStats() {
        this.timer = new GameTimer();
    }

    public void start() {
        timer.start();
    }

    public void finish() {
        timer.stop();
        finalSeconds = timer.getSeconds();
    }

    public int getDurationSeconds() {
        return finalSeconds >= 0 ? finalSeconds : timer.getSeconds();
    }

    public void registerPlayerShot(boolean hit) {
        playerShots++;
        if (hit) playerHits++;
    }

    public void registerBotShot(boolean hit) {
        botShots++;
        if (hit) botHits++;
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

    public void setFinalModel(BattleModel model) {
        this.finalModel = model;
    }

    public BattleModel getFinalModel() {
        return finalModel;
    }
}
