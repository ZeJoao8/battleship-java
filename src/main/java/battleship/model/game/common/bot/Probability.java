package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import battleship.model.game.common.Bot;

import java.util.*;

public class Probability extends Bot {

    private Boat currentTarget = null;
    private final List<Position> hitsCluster = new ArrayList<>();
    private final Queue<Position> targetQueue = new LinkedList<>();
    private final Queue<Position> pendingHits = new LinkedList<>();

    public Probability() {
        super("Probability", "Hard");
    }

    @Override
    public Position chooseMove(Board board) {
        if (!targetQueue.isEmpty()) {
            while (!targetQueue.isEmpty()) {
                Position p = targetQueue.poll();
                if (board.isInside(p) && board.isValidBotTarget(p))
                    return p;
            }
        }
        if (currentTarget == null && !pendingHits.isEmpty()) {
            while (!pendingHits.isEmpty()) {
                Position p = pendingHits.poll();
                Boat b = findBoatContaining(board, p);
                if (b != null && !b.isSunk()) {
                    startNewTarget(b, p, board);
                    return p;
                }
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
                        if (!board.isValidBotTarget(new Position(x + i, y))) {
                            valid = false; break;
                        }
                    }
                    if (valid)
                        for (int i = 0; i < size; i++)
                            heat[x + i][y]++;
                }
            }
            for (int x = 0; x < W; x++) {
                for (int y = 0; y <= H - size; y++) {
                    boolean valid = true;
                    for (int i = 0; i < size; i++) {
                        if (!board.isValidBotTarget(new Position(x, y + i))) {
                            valid = false; break;
                        }
                    }
                    if (valid)
                        for (int i = 0; i < size; i++)
                            heat[x][y + i]++;
                }
            }
        }

        int bestX = 0, bestY = 0, best = -1;
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                Position p = new Position(x, y);
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
        if (!board.isHit(p)) return;
        Boat hitBoat = findBoatContaining(board, p);
        if (hitBoat == null) return;
        if (currentTarget == null) {
            startNewTarget(hitBoat, p, board);
            return;
        }
        if (hitBoat == currentTarget) {
            hitsCluster.add(p);
            updateTargetQueue(board);
            if (currentTarget.isSunk()) {
                finishCurrentTarget(board);
            }
            return;
        }
        pendingHits.add(p);
    }

    private void startNewTarget(Boat boat, Position p, Board board) {
        currentTarget = boat;
        hitsCluster.clear();
        targetQueue.clear();
        hitsCluster.add(p);
        updateTargetQueue(board);
    }

    private void finishCurrentTarget(Board board) {
        hitsCluster.clear();
        targetQueue.clear();
        currentTarget = null;
        while (!pendingHits.isEmpty()) {
            Position p = pendingHits.poll();
            Boat b = findBoatContaining(board, p);
            if (b != null && !b.isSunk()) {
                startNewTarget(b, p, board);
                break;
            }
        }
    }

    private void updateTargetQueue(Board board) {
        targetQueue.clear();
        if (hitsCluster.size() == 1) {
            Position h = hitsCluster.get(0);
            addIfValid(board, new Position(h.getX() + 1, h.getY()));
            addIfValid(board, new Position(h.getX() - 1, h.getY()));
            addIfValid(board, new Position(h.getX(), h.getY() + 1));
            addIfValid(board, new Position(h.getX(), h.getY() - 1));
            return;
        }
        boolean horizontal = allSameY();
        boolean vertical = allSameX();
        hitsCluster.sort(Comparator.comparingInt(Position::getX).thenComparingInt(Position::getY));
        Position first = hitsCluster.get(0);
        Position last  = hitsCluster.get(hitsCluster.size() - 1);
        if (horizontal) {
            addIfValid(board, new Position(first.getX() - 1, first.getY()));
            addIfValid(board, new Position(last.getX() + 1, last.getY()));
        }
        else if (vertical) {
            addIfValid(board, new Position(first.getX(), first.getY() - 1));
            addIfValid(board, new Position(last.getX(), last.getY() + 1));
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

    private Boat findBoatContaining(Board board, Position p) {
        for (Boat b : board.getFleet()) {
            if (b.getPositions().contains(p))
                return b;
        }
        return null;
    }
}
