package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class ObstacleGraphics extends AbstractGameObject {

    private final TiledMapTileLayer groundLayer;

    public ObstacleGraphics(Texture texture, GridPoint2 initialCoordinates, TiledMapTileLayer groundLayer) {
        super(texture, initialCoordinates);
        this.groundLayer = groundLayer;
    }

    public void update(GridPoint2 currentCoordinates) {
        this.coordinates.set(currentCoordinates);
        moveRectangleAtTileCenter(groundLayer, rectangle, this.coordinates);
    }

    @Override
    public void update(float deltaTime) {
        throw new UnsupportedOperationException("ObstacleGraphics обновляется композитным классом Obstacle.");
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        throw new UnsupportedOperationException("ObstacleGraphics обновляется композитным классом Obstacle.");
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
