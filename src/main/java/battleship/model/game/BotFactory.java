package battleship.model.game;

import battleship.model.Settings;
import battleship.model.game.common.BotStrategy;
import battleship.model.game.common.bot.Hunt;
import battleship.model.game.common.bot.Perfect;
import battleship.model.game.common.bot.Probability;
import battleship.model.game.common.bot.Random;

public class BotFactory {

    public static BotStrategy create(Settings.BotDifficulty difficulty) {
        return switch (difficulty) {
            case EASY        -> new Random();
            case MEDIUM      -> new Hunt();
            case HARD        -> new Probability();
            case IMPOSSIBLE  -> new Perfect();
        };
    }
}
