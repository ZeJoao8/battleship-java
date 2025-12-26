package battleship.model.game;

import battleship.controller.game.TurnResult;
import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.BotStrategy;


public class BattleModel {

    private final Board playerBoard;
    private final Board enemyBoard;
    final BotStrategy bot;
    private final GameStats stats;
    private Position cursor = new Position(0, 0);

    public BattleModel(Board playerBoard, Board enemyBoard, BotStrategy bot) {
        this(playerBoard, enemyBoard, bot, new GameStats());
    }

    public BattleModel(Board playerBoard, Board enemyBoard, BotStrategy bot, GameStats stats) {
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
        this.bot = bot;
        this.stats = stats;
        this.stats.start();
    }

    public Board getPlayerBoard() { return playerBoard; }
    public Board getEnemyBoard()  { return enemyBoard; }
    public GameStats getStats() {return stats;}

    public Position getCursor() { return cursor; }
    public void setCursor(Position p) { cursor = p; }

    public boolean alreadyShotEnemy(Position p) {
        return enemyBoard.isHit(p) || enemyBoard.isMiss(p);
    }

    public void playerShoot(Position p) {
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

    public TurnResult playTurn(Position p) {
        if (alreadyShotEnemy(p))
            return TurnResult.CONTINUE;
        playerShoot(p);
        if (enemyLost()) {
            stats.finish();
            stats.setFinalModel(this);
            return TurnResult.PLAYER_WON;
        }
        makeBotMove();
        if (playerLost()) {
            stats.finish();
            stats.setFinalModel(this);
            return TurnResult.BOT_WON;
        }
        return TurnResult.CONTINUE;
    }

    public boolean isEnemyBoatVisible(Boat b) {
        return b.isSunk();
    }
}
