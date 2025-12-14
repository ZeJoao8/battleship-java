package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Bot;

import java.util.*;

public class Hunt extends Bot {

    private final Queue<Position> queue = new LinkedList<>();
    private final java.util.Random rnd = new java.util.Random();

    public Hunt() {
        super("Hunt", "Medium");
    }

    @Override
    public Position chooseMove(Board board) {
        while (!queue.isEmpty()) {
            Position p = queue.poll();
            if (board.isInside(p) && board.isValidBotTarget(p))
                return register(board, p);
        }
        Position p;
        do {p = new Position(rnd.nextInt(board.getWidth()), rnd.nextInt(board.getHeigth()));
        } while (!board.isValidBotTarget(p) || (p.getX() + p.getY()) % 2 != 0);


        return register(board, p);
    }

    private Position register(Board b, Position p) {
        if (b.isShip(p)) {
            add(b, new Position(p.getX()+1, p.getY()));
            add(b, new Position(p.getX()-1, p.getY()));
            add(b, new Position(p.getX(), p.getY()+1));
            add(b, new Position(p.getX(), p.getY()-1));
        }
        return p;
    }

    private void add(Board b, Position p) {
        if (b.isInside(p) && b.isValidBotTarget(p))
            queue.add(p);
    }
}
