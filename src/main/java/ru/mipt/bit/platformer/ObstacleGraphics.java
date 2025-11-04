package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class ObstacleGraphics extends AbstractGameObject {

    private final TiledMapTileLayer groundLayer;

    public ObstacleGraphics(Texture texture, GridPoint2 initialCoordinates, TiledMapTileLayer groundLayer) {
        super(texture, initialCoordinates);
        this.groundLayer = groundLayer;
        moveRectangleAtTileCenter(groundLayer, rectangle, this.coordinates);
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
