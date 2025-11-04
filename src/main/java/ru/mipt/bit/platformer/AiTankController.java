package ru.mipt.bit.platformer;

import java.util.Random;

public class AiTankController {

    private final GameUnitModel aiTankModel;
    private CommandProcessor commandProcessor;
    private final Random random;

    public AiTankController(GameUnitModel aiTankModel, CommandProcessor commandProcessor) {
        this.aiTankModel = aiTankModel;
        this.commandProcessor = commandProcessor;
        this.random = new Random();
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public void update() {
        if (aiTankModel.getMovementProgress() == 1f && aiTankModel.getCurrentDirection() == null) {
            Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
            commandProcessor.addCommand(new MoveCommand(aiTankModel, randomDirection));
        }
    }
}
