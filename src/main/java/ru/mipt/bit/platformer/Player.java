package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

public class Player implements GameObject {

    private final PlayerModel model;
    private final PlayerGraphics graphics;
    private final TiledMapTileLayer groundLayer; // Need this for TileMovement for graphics to know tile sizes
    private final InputHandler inputHandler;

    public Player(Texture texture, GridPoint2 initialCoordinates, TiledMapTileLayer groundLayer, Interpolation interpolation, CollisionDetector collisionDetector, InputHandler inputHandler) {
        this.groundLayer = groundLayer;
        this.inputHandler = inputHandler;
        TileMovement tileMovement = new TileMovement(groundLayer, interpolation);
        this.model = new PlayerModel(initialCoordinates, collisionDetector, tileMovement);
        this.graphics = new PlayerGraphics(texture, model.getCoordinates(), model.getRotation());
    }

    @Override
    public void update(float deltaTime) {
        throw new UnsupportedOperationException("Use update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) instead.");
    }

    @Override
    public void update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) {
        Direction intendedDirection = inputHandler.getDirection();
        inputHandler.resetDirection(); // Reset direction after processing

        model.update(deltaTime, collidableObjects, intendedDirection);

        // Update graphics based on model state
        graphics.update(model.getCoordinates(), model.getRotation(), model.getDestinationCoordinates(), model.getMovementProgress(), model.getTileMovement(), groundLayer);
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
