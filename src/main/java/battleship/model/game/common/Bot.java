package battleship.model.game.common;

import battleship.model.Position;


public abstract class Bot implements BotStrategy {

    private final String name;
    private final String difficulty;

    public Bot(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public abstract Position chooseMove(Board board);
}

