package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.game.common.Board;
import battleship.model.game.common.Bot;

public class Bolachas extends Bot {

    public Bolachas() {
        super("Bolachas", "Impossível");
    }

    @Override
    public Position chooseMove(Board board) {
        int width = board.getWidth();
        int height = board.getHeigth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position p = new Position(x, y);

                if (board.isShip(p) && board.isValidBotTarget(p)) {
                    return p;
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position p = new Position(x, y);
                if (board.isValidBotTarget(p)) return p;
            }
        }

        return new Position(0, 0);
    }
}


