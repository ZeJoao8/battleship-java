package battleship.model.game.common;


import battleship.model.Position;


public interface BotStrategy {
    Position chooseMove(Board board);
    default void registerShot(Board board, Position p) {}
}
