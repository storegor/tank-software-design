package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class Obstacle implements GameObject {

    private final ObstacleModel model;
    private final ObstacleGraphics graphics;

    public Obstacle(Texture texture, GridPoint2 initialCoordinates, TiledMapTileLayer groundLayer) {
        this.model = new ObstacleModel(initialCoordinates);
        this.graphics = new ObstacleGraphics(texture, model.getCoordinates(), groundLayer);
    }

    @Override
    public void update(float deltaTime) {
        throw new UnsupportedOperationException("Obstacle обновляется композитным классом Obstacle. Используйте update(float deltaTime, List<? extends GameObject> collidableObjects) вместо этого.");
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

    public void dispose() {
        graphics.dispose();
    }
} 
