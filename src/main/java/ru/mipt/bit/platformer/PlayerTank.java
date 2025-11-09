package ru.mipt.bit.platformer;

import java.util.List;

public class PlayerTank extends Tank {

    private final InputHandler inputHandler;

    public PlayerTank(GameUnitModel model, GameUnitGraphics graphics, List<HealthBarGraphicsDecorator> decorators) {
        super(model, graphics);
        this.inputHandler = new KeyboardInputHandler(this.getModel(), this.getCommandProcessor(), decorators);
    }

    @Override
    public void update(float deltaTime, java.util.List<? extends GameObject> collidableObjects) {
        super.update(deltaTime, collidableObjects);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
