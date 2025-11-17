package BattleShip.Model;

import java.util.Random;

public class BattleModel {

    private final Board playerBoard;
    private final Board enemyBoard;

    private final Random rnd = new Random();

    public BattleModel(Board playerBoard, Board enemyBoard) {
        this.playerBoard = playerBoard;
        this.enemyBoard = enemyBoard;
    }

    public Board getPlayerBoard() { return playerBoard; }
    public Board getEnemyBoard() { return enemyBoard; }

    public boolean isAlreadyShotEnemy(int x, int y) {
        int cell = enemyBoard.getCell(x, y);
        return cell == Board.Hit || cell == Board.Miss;
    }

    public void playerShoot(int x, int y) {
        int cell = enemyBoard.getCell(x, y);

        if (cell == Board.Water) {
            enemyBoard.markMiss(x, y);
        } else if (cell == Board.Ship) {
            enemyBoard.markHit(x, y);
        }
    }

    public void botTurn() {
        int w = playerBoard.getWidth();
        int h = playerBoard.getHeigth();

        int x, y;

        do {
            x = rnd.nextInt(w);
            y = rnd.nextInt(h);
        } while (!playerBoard.isValidBotTarget(x, y));

        int cell = playerBoard.getCell(x, y);

        if (cell == Board.Ship) {
            playerBoard.markHit(x, y);
        } else {
            playerBoard.markMiss(x, y);
        }
    }

    public boolean hasEnemyLost() {
        return enemyBoard.hasLost();
    }

    public boolean hasPlayerLost() {
        return playerBoard.hasLost();
    }


}
