package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Obstacle implements GameObject, Disposable {

    private final ObstacleModel model;
    private final ObstacleGraphics graphics;

    public Obstacle(ObstacleModel model, ObstacleGraphics graphics) {
        this.model = model;
        this.graphics = graphics;
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void update(float deltaTime, java.util.List<? extends GameObject> collidableObjects) {
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
