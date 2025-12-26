package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.Bot;

import java.util.*;

public class Probability extends Bot {

    private final Queue<Position> targetQueue = new LinkedList<>();
    private final List<Position> hitsCluster = new ArrayList<>();

    public Probability() {
        super("Probability", "Hard");
    }

    @Override
    public Position chooseMove(Board board) {
        if (!targetQueue.isEmpty()) {
            while (!targetQueue.isEmpty()) {
                Position next = targetQueue.poll();
                if (board.isInside(next) && board.isValidBotTarget(next))
                    return next;
            }
        }
        return chooseHuntMove(board);
    }

    private Position chooseHuntMove(Board board) {
        int W = board.getWidth();
        int H = board.getHeight();
        int[][] heat = new int[W][H];
        for (Boat boat : board.getFleet()) {
            if (boat.isSunk()) continue;
            int size = boat.getSize();
            for (int y = 0; y < H; y++) {
                for (int x = 0; x <= W - size; x++) {
                    boolean valid = true;
                    for (int i = 0; i < size; i++) {
                        Position p = new Position(x+i, y);
                        if (!board.isValidBotTarget(p)) {
                            valid = false; break;
                        }
                    }
                    if (valid) {
                        for (int i = 0; i < size; i++)
                            heat[x+i][y]++;
                    }
                }
            }
            for (int x = 0; x < W; x++) {
                for (int y = 0; y <= H - size; y++) {
                    boolean valid = true;
                    for (int i = 0; i < size; i++) {
                        Position p = new Position(x, y+i);
                        if (!board.isValidBotTarget(p)) {
                            valid = false; break;
                        }
                    }
                    if (valid) {
                        for (int i = 0; i < size; i++)
                            heat[x][y+i]++;
                    }
                }
            }
        }
        int bestX = 0, bestY = 0, best = -1;
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                Position p = new Position(x,y);
                if (!board.isValidBotTarget(p)) continue;
                if (heat[x][y] > best) {
                    best = heat[x][y];
                    bestX = x;
                    bestY = y;
                }
            }
        }
        return new Position(bestX, bestY);
    }

    @Override
    public void registerShot(Board board, Position p) {
        boolean isHit = board.isHit(p);
        if (isHit) {
            hitsCluster.add(p);
            updateTargetQueue(board);
        }
        if (clusterSunk(board)) {
            hitsCluster.clear();
            targetQueue.clear();
        }
    }

    private void updateTargetQueue(Board board) {
        targetQueue.clear();
        if (hitsCluster.size() == 1) {
            Position h = hitsCluster.get(0);
            addIfValid(board, new Position(h.getX()+1, h.getY()));
            addIfValid(board, new Position(h.getX()-1, h.getY()));
            addIfValid(board, new Position(h.getX(), h.getY()+1));
            addIfValid(board, new Position(h.getX(), h.getY()-1));
            return;
        }
        boolean horizontal = allSameY();
        boolean vertical = allSameX();
        hitsCluster.sort(Comparator.comparingInt(Position::getX)
                .thenComparingInt(Position::getY));
        Position first = hitsCluster.get(0);
        Position last  = hitsCluster.get(hitsCluster.size()-1);
        if (horizontal) {
            addIfValid(board, new Position(first.getX()-1, first.getY()));
            addIfValid(board, new Position(last.getX()+1,  last.getY()));
        }
        else if (vertical) {
            addIfValid(board, new Position(first.getX(), first.getY()-1));
            addIfValid(board, new Position(last.getX(),  last.getY()+1));
        }
    }

    private boolean allSameX() {
        int x = hitsCluster.get(0).getX();
        return hitsCluster.stream().allMatch(p -> p.getX() == x);
    }

    private boolean allSameY() {
        int y = hitsCluster.get(0).getY();
        return hitsCluster.stream().allMatch(p -> p.getY() == y);
    }

    private void addIfValid(Board board, Position p) {
        if (board.isInside(p) && board.isValidBotTarget(p))
            targetQueue.add(p);
    }

    private boolean clusterSunk(Board board) {
        for (Boat b : board.getFleet()) {
            if (!b.isSunk()) {
                for (Position h : hitsCluster)
                    if (b.getPositions().contains(h))
                        return false;
            }
        }
        return true;
    }
}
