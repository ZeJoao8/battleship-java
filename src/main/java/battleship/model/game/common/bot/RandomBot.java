package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Bot;

import java.util.Random;

public class RandomBot extends Bot {

    private final Random rnd = new Random();

    public RandomBot() {
        super("Random Bot", "Fácil");
    }

    @Override
    public Position chooseMove(Board board) {
        Position p;
        do {
            p = new Position(rnd.nextInt(board.getWidth()), rnd.nextInt(board.getHeigth()));
        } while (!board.isValidBotTarget(p));

        return p;
    }
}
