package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.bot.PerfectBot;
import battleship.model.game.common.bot.HuntTargetBot;
import battleship.model.game.common.bot.ProbabilityBot;
import battleship.model.game.common.bot.RandomBot;
import battleship.model.game.common.BotStrategy;


public class BattleModel {

    private final Board playerBoard;
    private final Board enemyBoard;
    private final Settings settings;
    final BotStrategy bot;
    private final GameStats stats;
    private Position cursor = new Position(0, 0);

    public BattleModel(Board playerBoard, Board enemyBoard, Settings settings) {
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
        this.settings = settings;
        this.stats= new GameStats();
        this.bot = switch (settings.getDifficulty()) {
            case RANDOM ->  new RandomBot();
            case HUNT   -> new HuntTargetBot();
            case PROBABILITY -> new ProbabilityBot();
            case PERFECT   -> new PerfectBot();
        };
    }

    public Board getPlayerBoard() { return playerBoard; }
    public Board getEnemyBoard()  { return enemyBoard; }
    public GameStats getStats() {return stats;}

    public Position getCursor() { return cursor; }
    public void setCursor(Position p) { cursor = p; }

    public boolean alreadyShotEnemy(Position p) {
        return enemyBoard.isHit(p) || enemyBoard.isMiss(p);
    }

    public void shootPlayer(Position p) {
        if (enemyBoard.isWater(p)) enemyBoard.markMiss(p);
        else if (enemyBoard.isShip(p)) enemyBoard.markHit(p);
        boolean hit = enemyBoard.isHit(p);
        stats.registerPlayerShot(hit);
    }

    public void makeBotMove() {
        Position botShot = bot.chooseMove(playerBoard);
        applyShot(playerBoard, botShot);
        boolean hit = playerBoard.isHit(botShot);
        stats.registerBotShot(hit);
        bot.registerShot(playerBoard, botShot);
    }

    void applyShot(Board b, Position p) {
        if (b.isShip(p)) b.markHit(p);
        else b.markMiss(p);
    }

    public boolean shouldRevealBoat(Boat b) { return b.isSunk(); }
    public boolean enemyLost() { return enemyBoard.hasLost(); }
    public boolean playerLost() { return playerBoard.hasLost(); }
}
