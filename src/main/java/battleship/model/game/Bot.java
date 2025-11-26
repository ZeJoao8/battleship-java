package battleship.model.game;

import battleship.model.Position;
import battleship.model.game.common.Board;

import java.util.*;

public class Bot {
    private final Set<Position> tried = new HashSet<>();
    private final Queue<Position> targetQueue = new LinkedList<>();
    private final Random rnd = new Random();

    public Position chooseMove(Board board) {
        while (!targetQueue.isEmpty()) {
            Position p = targetQueue.poll();
            if (!tried.contains(p) && board.isInside(p))
                return register(board,p);
        }
        Position p;
        int w=board.getWidth();
        int h=board.getHeigth();

        do{
            p=new Position(rnd.nextInt(w),rnd.nextInt(h));
        } while (tried.contains(p));

        return register(board,p);
    }

    private Position register(Board board,Position p) {
        tried.add(p);

        if(board.isShip(p)) {
            tryAdd(new Position(p.getX() + 1,p.getY()),board);
            tryAdd(new Position(p.getX() - 1,p.getY()),board);
            tryAdd(new Position(p.getX(), p.getY() + 1), board);
            tryAdd(new Position(p.getX(), p.getY() - 1), board);

        }
        return p;

    }
    private void tryAdd(Position p,Board b) {
        if (b.isInside(p) && !tried.contains(p))
            targetQueue.add(p);

    }


}