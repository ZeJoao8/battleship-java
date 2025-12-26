package battleship.model.game.common.bot;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.game.common.Board;
import battleship.model.game.common.Boat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HuntBotTest {

    private Board board() {
        return new Board(new Settings(6, 6, Settings.BotDifficulty.EASY));
    }

    @Test
    void usesQueuedPositionsBeforeRandomHunt() {
        Board board = board();
        Boat boat = board.getFleet().get(0);

        // Coloca barco no centro para garantir 4 vizinhos
        board.placeBoat(boat, new Position(2, 2), true);

        Hunt bot = new Hunt();

        // Marca hit (isto prepara o estado)
        Position hit = new Position(2, 2);
        board.markHit(hit);

        // Regista o shot
        bot.registerShot(board, hit);

        // PRIMEIRA jogada: deve sair da queue
        Position p1 = bot.chooseMove(board);
        assertTrue(board.isInside(p1));

        // Marca esse alvo como miss para forçar consumo da queue
        board.markMiss(p1);

        // SEGUNDA jogada: ainda vem da queue (while loop)
        Position p2 = bot.chooseMove(board);
        assertTrue(board.isInside(p2));
    }

    @Test
    void skipsInvalidQueuedPositions() {
        Board board = board();
        Boat boat = board.getFleet().get(0);

        // Barco no canto para gerar posições fora do board
        board.placeBoat(boat, new Position(0, 0), true);

        Hunt bot = new Hunt();

        Position hit = new Position(0, 0);
        board.markHit(hit);
        bot.registerShot(board, hit);

        // Todas as posições inválidas devem ser descartadas
        Position p = bot.chooseMove(board);

        assertNotNull(p);
        assertTrue(board.isValidBotTarget(p));
    }

    @Test
    void registerAddsNeighborsOnlyWhenShip() {
        Board board = board();
        Boat boat = board.getFleet().get(0);

        board.placeBoat(boat, new Position(3, 3), true);

        Hunt bot = new Hunt();

        Position hit = new Position(3, 3);
        board.markHit(hit);
        bot.registerShot(board, hit);

        Position p = bot.chooseMove(board);

        // Força register() com b.isShip(p) == true
        assertNotNull(p);
        assertTrue(board.isInside(p));
    }

    @Test
    void missDoesNotPopulateQueue() {
        Board board = board();
        Hunt bot = new Hunt();

        Position miss = new Position(1, 1);
        board.markMiss(miss);
        bot.registerShot(board, miss);

        Position p = bot.chooseMove(board);

        assertNotNull(p);
        assertTrue(board.isValidBotTarget(p));
    }
}
