package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Bot;

public class Random extends Bot {

    private final java.util.Random rnd = new java.util.Random();

    public Random() {
        super("Random", "Easy");
    }

    @Override
    public Position chooseMove(Board board) {
        Position p;
        do {
            p = new Position(rnd.nextInt(board.getWidth()), rnd.nextInt(board.getHeight()));
        } while (!board.isValidBotTarget(p));

        return p;
    }
}
