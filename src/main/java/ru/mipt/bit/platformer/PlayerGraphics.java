package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

public class PlayerGraphics extends AbstractGameObject {

    public PlayerGraphics(Texture texture, GridPoint2 initialCoordinates, float initialRotation) {
        super(texture, initialCoordinates);
        this.rotation = initialRotation;
    }

    public void update(GridPoint2 currentCoordinates, float currentRotation, GridPoint2 destinationCoordinates, float movementProgress, TileMovement tileMovement, TiledMapTileLayer groundLayer) {
        this.coordinates.set(currentCoordinates);
        this.rotation = currentRotation;
        tileMovement.moveRectangleBetweenTileCenters(rectangle, this.coordinates, destinationCoordinates, movementProgress);
    }

    @Override
    public void update(float deltaTime) {
        // This method is not used as PlayerGraphics is updated by Player composite
        throw new UnsupportedOperationException("PlayerGraphics is updated by Player composite. Use update(GridPoint2, float, GridPoint2, float, TileMovement, TiledMapTileLayer) instead.");
    }

    @Override
    public void update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) {
        // This method is not used as PlayerGraphics is updated by Player composite
        throw new UnsupportedOperationException("PlayerGraphics is updated by Player composite. Use update(GridPoint2, float, GridPoint2, float, TileMovement, TiledMapTileLayer) instead.");
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
