package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;

import java.util.Random;

public class RandomBot implements BotStrategy {

    private final Random rnd = new Random();

    @Override
    public Position chooseMove(Board board) {
        Position p;
        do {
            p = new Position(rnd.nextInt(board.getWidth()), rnd.nextInt(board.getHeigth()));
        } while (!board.isValidBotTarget(p));

        return p;
    }
}
