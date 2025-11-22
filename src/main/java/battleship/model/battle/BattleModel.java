package battleship.model.battle;

import battleship.model.Position;
import battleship.model.common.Board;

import java.util.Random;

public class BattleModel {

    private final Board playerBoard;
    private final Board enemyBoard;
    private final Random rnd = new Random();

    private Position viewCursor = new Position(0, 0);

    public BattleModel(Board playerBoard, Board enemyBoard) {
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
    }

    public Board getPlayerBoard() { return playerBoard; }
    public Board getEnemyBoard()  { return enemyBoard; }

    public Position getViewCursor() { return viewCursor; }

    public void setViewCursor(Position p) {
        this.viewCursor = p;
    }

    public boolean isAlreadyShotEnemy(Position p) {
        int cell = enemyBoard.getCell(p);
        return cell == Board.Hit || cell == Board.Miss;
    }

    public void playerShoot(Position p) {
        int cell = enemyBoard.getCell(p);
        if (cell == Board.Water) enemyBoard.markMiss(p);
        else if (cell == Board.Ship) enemyBoard.markHit(p);
    }

    public void botTurn() {
        int w = playerBoard.getWidth();
        int h = playerBoard.getHeigth();

        Position p;
        do {
            p = new Position(rnd.nextInt(w), rnd.nextInt(h));
        } while (!playerBoard.isValidBotTarget(p));

        int cell = playerBoard.getCell(p);
        if (cell == Board.Ship) playerBoard.markHit(p);
        else playerBoard.markMiss(p);
    }

    public boolean hasEnemyLost() { return enemyBoard.hasLost(); }
    public boolean hasPlayerLost() { return playerBoard.hasLost(); }
}
