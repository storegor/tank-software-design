package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player extends AbstractGameObject {

    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final CollisionDetector collisionDetector;
    private List<? extends AbstractGameObject> currentCollidableObjects;

    public Player(Texture texture, GridPoint2 initialCoordinates, TiledMapTileLayer groundLayer, Interpolation interpolation, CollisionDetector collisionDetector) {
        super(texture, initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.tileMovement = new TileMovement(groundLayer, interpolation);
        this.collisionDetector = collisionDetector;
    }

    @Override
    public void update(float deltaTime) {
        throw new UnsupportedOperationException("Use update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) instead.");
    }

    public void update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) {
        this.currentCollidableObjects = collidableObjects;
        handleInput();
        updateMovement(deltaTime);
    }

    private void handleInput() {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 potentialDestination = null;
            float potentialRotation = rotation;

            if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
                potentialDestination = incrementedY(coordinates);
                potentialRotation = 90f;
            } else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
                potentialDestination = decrementedX(coordinates);
                potentialRotation = -180f;
            } else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
                potentialDestination = decrementedY(coordinates);
                potentialRotation = -90f;
            } else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
                potentialDestination = incrementedX(coordinates);
                potentialRotation = 0f;
            }

            if (potentialDestination != null && !isColliding(potentialDestination)) {
                destinationCoordinates.set(potentialDestination);
                rotation = potentialRotation;
                movementProgress = 0f;
            }
        }
    }

    private void updateMovement(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    private boolean isColliding(GridPoint2 targetCoordinates) {
        return collisionDetector.isColliding(targetCoordinates, currentCollidableObjects);
    }
} 