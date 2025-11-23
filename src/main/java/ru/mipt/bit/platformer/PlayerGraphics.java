package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

public class PlayerGraphics extends AbstractGameObject implements GameUnitGraphics {

    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;

    public PlayerGraphics(Texture texture, GridPoint2 initialCoordinates, float initialRotation, TiledMapTileLayer groundLayer, Interpolation interpolation) {
        super(texture, initialCoordinates);
        this.rotation = initialRotation;
        this.groundLayer = groundLayer;
        this.tileMovement = new TileMovement(groundLayer, interpolation);
    }

    @Override
    public void update(GridPoint2 currentCoordinates, float currentRotation, GridPoint2 destinationCoordinates, float movementProgress) {
        this.coordinates.set(currentCoordinates);
        this.rotation = currentRotation;
        tileMovement.moveRectangleBetweenTileCenters(rectangle, this.coordinates, destinationCoordinates, movementProgress);
    }

    @Override
    public void update(GridPoint2 currentCoordinates) {
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
    }
}
