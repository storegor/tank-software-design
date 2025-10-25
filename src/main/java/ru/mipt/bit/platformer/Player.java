package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.util.Collections;
import java.util.List;

public class Player implements GameObject, Disposable {

    private final GameUnitModel model;
    private final GameUnitGraphics graphics;
    private final InputHandler inputHandler;

    public Player(GameUnitModel model, GameUnitGraphics graphics, InputHandler inputHandler) {
        this.model = model;
        this.graphics = graphics;
        this.inputHandler = inputHandler;
    }

    @Override
    public void update(float deltaTime) {
        update(deltaTime, Collections.emptyList());
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        Direction intendedDirection = inputHandler.getDirection();
        inputHandler.resetDirection(); 

        model.update(deltaTime, collidableObjects, intendedDirection);

        graphics.update(model.getCoordinates(), model.getRotation(), model.getDestinationCoordinates(), model.getMovementProgress());
    }

    @Override
    public void render(Batch batch) {
        graphics.render(batch);
    }

    public GridPoint2 getCoordinates() {
        return model.getCoordinates();
    }

    public Rectangle getRectangle() {
        return graphics.getRectangle();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }
}
