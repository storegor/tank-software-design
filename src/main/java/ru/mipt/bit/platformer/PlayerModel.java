package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerModel {

    private static final float MOVEMENT_SPEED = 0.4f;

    private final TileMovement tileMovement;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final CollisionDetector collisionDetector;
    private List<? extends GameObject> currentCollidableObjects;
    private float rotation;

    public PlayerModel(GridPoint2 initialCoordinates, CollisionDetector collisionDetector, TileMovement tileMovement) {
        this.coordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.tileMovement = tileMovement;
        this.collisionDetector = collisionDetector;
        this.rotation = 0f;
    }

    public void update(float deltaTime, List<? extends GameObject> collidableObjects, Direction intendedDirection) {
        this.currentCollidableObjects = collidableObjects;

        if (isEqual(movementProgress, 1f) && intendedDirection != null) {
            GridPoint2 potentialDestination = coordinates.cpy().add(intendedDirection.getVector());
            if (!isColliding(potentialDestination)) {
                destinationCoordinates.set(potentialDestination);
                rotation = intendedDirection.getRotation();
                movementProgress = 0f;
            }
        }

        updateMovement(deltaTime);
    }

    private void updateMovement(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    private boolean isColliding(GridPoint2 targetCoordinates) {
        return collisionDetector.isColliding(targetCoordinates, currentCollidableObjects);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }
}
