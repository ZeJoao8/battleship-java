package battleship.model.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.bot.PerfectBot;
import battleship.model.game.common.bot.HuntTargetBot;
import battleship.model.game.common.bot.RandomBot;
import battleship.model.game.common.BotStrategy;


public class BattleModel {

    private final Board playerBoard;
    private final Board enemyBoard;
    private final Settings settings;
    final BotStrategy bot;

    private Position cursor = new Position(0, 0);

    public BattleModel(Board playerBoard, Board enemyBoard, Settings settings) {
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
        this.settings = settings;

        this.bot = switch (settings.getDifficulty()) {
            case RANDOM ->  new RandomBot();
            case HUNT   -> new HuntTargetBot();
            case PERFECT   -> new PerfectBot();
        };
    }

    public Board getPlayerBoard() { return playerBoard; }
    public Board getEnemyBoard()  { return enemyBoard; }

    public Position getCursor() { return cursor; }
    public void setCursor(Position p) { cursor = p; }

    public boolean alreadyShotEnemy(Position p) {
        return enemyBoard.isHit(p) || enemyBoard.isMiss(p);
    }

    public void shootPlayer(Position p) {
        if (enemyBoard.isWater(p)) enemyBoard.markMiss(p);
        else if (enemyBoard.isShip(p)) enemyBoard.markHit(p);
    }

    public void makeBotMove() {
        Position botShot = bot.chooseMove(playerBoard);
        applyShot(playerBoard, botShot);
    }

    void applyShot(Board b, Position p) {
        if (b.isShip(p)) b.markHit(p);
        else b.markMiss(p);
    }

    public boolean shouldRevealBoat(Boat b) { return b.isSunk(); }
    public boolean enemyLost() { return enemyBoard.hasLost(); }
    public boolean playerLost() { return playerBoard.hasLost(); }
}
