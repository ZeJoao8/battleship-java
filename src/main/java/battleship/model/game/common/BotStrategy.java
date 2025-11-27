package battleship.model.game.common;


import battleship.model.Position;


public interface BotStrategy {
    Position chooseMove(Board board);
}
