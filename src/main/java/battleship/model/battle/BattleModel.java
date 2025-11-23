package battleship.model.battle;

import battleship.model.Position;
import battleship.model.common.Board;
import battleship.model.common.Boat;

import java.util.Random;

public class BattleModel {

    private final Board playerBoard;
    private final Board enemyBoard;
    private final Random rnd = new Random();

    private Position cursor = new Position(0, 0);

    public BattleModel(Board playerBoard, Board enemyBoard) {
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
    }

    public Board getPlayerBoard() { return playerBoard; }
    public Board getEnemyBoard()  { return enemyBoard; }
    public Position getCursor() { return cursor; }
    public void setCursor(Position p) { this.cursor = p; }

    public boolean alreadyShotEnemy(Position p) {
        return enemyBoard.isHit(p) || enemyBoard.isMiss(p);
    }

    public void shootPlayer(Position p) {
        if (enemyBoard.isWater(p)) enemyBoard.markMiss(p);
        else if (enemyBoard.isShip(p)) enemyBoard.markHit(p);
    }

    public void makeBotMove() {
        Position p = randomValidTarget(playerBoard);
        applyShot(playerBoard, p);
    }

    private Position randomValidTarget(Board b) {
        int w = b.getWidth();
        int h = b.getHeigth();

        Position p;
        do {
            p = new Position(rnd.nextInt(w), rnd.nextInt(h));
        } while (!b.isValidBotTarget(p));

        return p;
    }

    private void applyShot(Board b, Position p) {
        if (b.isShip(p)) b.markHit(p);
        else b.markMiss(p);
    }

    public boolean shouldRevealBoat(Boat b) {
        return b.isSunk();
    }

    public boolean enemyLost() { return enemyBoard.hasLost(); }
    public boolean playerLost() { return playerBoard.hasLost(); }
}
