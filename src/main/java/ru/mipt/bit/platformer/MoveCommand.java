package ru.mipt.bit.platformer;

public class MoveCommand implements Command {
    private final GameUnitModel tankModel;
    private final Direction direction;

    public MoveCommand(GameUnitModel tankModel, Direction direction) {
        this.tankModel = tankModel;
        this.direction = direction;
    }

    public GameUnitModel getTankModel() {
        return tankModel;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void execute() {
        tankModel.setDirection(direction);
    }
}
