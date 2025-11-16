package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class BulletGraphics implements GameUnitGraphics {
    private final ShapeRenderer shapeRenderer;
    private final Rectangle rectangle;
    private final Interpolation interpolation = Interpolation.linear;
    private final TiledMapTileLayer tileLayer;
    private final int tileWidth;
    private final int tileHeight;

    public BulletGraphics(GridPoint2 initialCoordinates, TiledMapTileLayer tileLayer) {
        this.shapeRenderer = new ShapeRenderer();
        this.tileLayer = tileLayer;
        this.tileWidth = tileLayer.getTileWidth();
        this.tileHeight = tileLayer.getTileHeight();
        this.rectangle = new Rectangle(0, 0, 8, 8);
        GdxGameUtils.moveRectangleAtTileCenter(tileLayer, rectangle, initialCoordinates);
    }

    @Override
    public void render(Batch batch) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void update(GridPoint2 currentCoordinates, float rotation, GridPoint2 destinationCoordinates, float movementProgress) {
        float alpha = interpolation.apply(movementProgress);
        float tileX = MathUtils.lerp(currentCoordinates.x, destinationCoordinates.x, alpha);
        float tileY = MathUtils.lerp(currentCoordinates.y, destinationCoordinates.y, alpha);
        
        float centerX = tileX * tileWidth + tileWidth / 2f;
        float centerY = tileY * tileHeight + tileHeight / 2f;
        rectangle.setCenter(centerX, centerY);
    }

    @Override
    public void update(GridPoint2 currentCoordinates) {
        GdxGameUtils.moveRectangleAtTileCenter(tileLayer, rectangle, currentCoordinates);
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
        try {
            shapeRenderer.dispose();
        } catch (IllegalArgumentException e) {
        }
    }
}

