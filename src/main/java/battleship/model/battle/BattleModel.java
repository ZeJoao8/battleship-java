package battleship.model.battle;

import battleship.model.Position;
import battleship.model.common.Board;

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
        int cell = enemyBoard.getCell(p);
        return cell == Board.HIT || cell == Board.MISS;
    }

    public void shootPlayer(Position p) {
        int cell = enemyBoard.getCell(p);
        if (cell == Board.WATER) enemyBoard.markMiss(p);
        else if (cell == Board.SHIP) enemyBoard.markHit(p);
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
        int cell = b.getCell(p);
        if (cell == Board.SHIP) b.markHit(p);
        else b.markMiss(p);
    }

    public boolean enemyLost() { return enemyBoard.hasLost(); }
    public boolean playerLost() { return playerBoard.hasLost(); }
}
