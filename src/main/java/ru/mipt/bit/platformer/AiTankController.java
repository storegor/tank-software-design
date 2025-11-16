package ru.mipt.bit.platformer;

import java.util.Random;

public class AiTankController {

    private final GameUnitModel aiTankModel;
    private CommandProcessor commandProcessor;
    private final Random random;
    private GameWorld gameWorld;

    public AiTankController(GameUnitModel aiTankModel, CommandProcessor commandProcessor) {
        this.aiTankModel = aiTankModel;
        this.commandProcessor = commandProcessor;
        this.random = new Random();
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void update() {
        if (aiTankModel.getMovementProgress() == 1f && aiTankModel.getCurrentDirection() == null) {
            if (gameWorld != null && random.nextFloat() < 0.1f) {
                commandProcessor.addCommand(new ShootCommand(aiTankModel, gameWorld));
            } else {
                Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
                commandProcessor.addCommand(new MoveCommand(aiTankModel, randomDirection));
            }
        }
    }
}
