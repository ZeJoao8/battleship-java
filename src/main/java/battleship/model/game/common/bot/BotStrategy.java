package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;

public interface BotStrategy {
    Position chooseMove(Board board);
}
