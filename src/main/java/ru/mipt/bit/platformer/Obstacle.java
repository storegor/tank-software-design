package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.util.List;

public class Obstacle implements GameObject, Disposable {

    private final GameUnitModel model;
    private final GameUnitGraphics graphics;

    public Obstacle(GameUnitModel model, GameUnitGraphics graphics) {
        this.model = model;
        this.graphics = graphics;
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        graphics.update(model.getCoordinates());
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

    @Override
    public void dispose() {
        graphics.dispose();
    }
} 
