package ru.mipt.bit.platformer;

public class PlayerTank extends Tank {

    private final InputHandler inputHandler;

    public PlayerTank(GameUnitModel model, GameUnitGraphics graphics) {
        super(model, graphics);
        this.inputHandler = new KeyboardInputHandler(this.getModel(), this.getCommandProcessor());
    }

    @Override
    public void update(float deltaTime, java.util.List<? extends GameObject> collidableObjects) {
        super.update(deltaTime, collidableObjects);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
