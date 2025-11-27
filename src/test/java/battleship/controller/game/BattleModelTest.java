package battleship.controller.game;

import battleship.model.Position;
import battleship.model.Settings;
import battleship.model.Settings.BotDifficulty;
import battleship.model.game.BattleModel;
import battleship.model.game.common.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BattleModelTest {

    @Test
    void shootPlayerMarksHitOrMiss() {
        Settings settings = new Settings(10, 10, BotDifficulty.RANDOM);

        Board player = new Board(settings);
        Board enemy = new Board(settings);

        BattleModel model = new BattleModel(player, enemy, settings);

        Position hitPos = new Position(1, 1);
        enemy.placeBoat(enemy.getFleet().get(4), hitPos, true);

        model.shootPlayer(hitPos);
        assertTrue(enemy.isHit(hitPos));

        Position missPos = new Position(3, 3);
        model.shootPlayer(missPos);
        assertTrue(enemy.isMiss(missPos));
    }
}
