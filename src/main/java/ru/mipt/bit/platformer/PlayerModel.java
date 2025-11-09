package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class PlayerModel implements GameUnitModel {

    private static final int MAX_HP = 100;
    private final float movementSpeed;
    private int hp;
    private final int maxHp;

    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final CollisionDetector collisionDetector;
    private List<? extends GameObject> currentCollidableObjects;
    private float rotation;
    private Direction currentDirection;

    public PlayerModel(GridPoint2 initialCoordinates, CollisionDetector collisionDetector, float movementSpeed) {
        this.coordinates = new GridPoint2(initialCoordinates);
        this.destinationCoordinates = new GridPoint2(initialCoordinates);
        this.collisionDetector = collisionDetector;
        this.rotation = 0f;
        this.movementSpeed = movementSpeed;
        this.currentDirection = null;
        this.maxHp = MAX_HP;
        this.hp = MAX_HP;
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        this.currentCollidableObjects = collidableObjects;

        if (isEqual(movementProgress, 1f) && currentDirection != null) {
            GridPoint2 potentialDestination = coordinates.cpy().add(currentDirection.getVector());
            if (!isColliding(potentialDestination)) {
                destinationCoordinates.set(potentialDestination);
                rotation = currentDirection.getRotation();
                movementProgress = 0f;
            } else {
                currentDirection = null;
            }
        }

        updateMovement(deltaTime);
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    private void updateMovement(float deltaTime) {
        float oldProgress = movementProgress;
        movementProgress = continueProgress(oldProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f) && !isEqual(oldProgress, 1f)) {
            coordinates.set(destinationCoordinates);
            currentDirection = null;
        }
    }

    private boolean isColliding(GridPoint2 targetCoordinates) {
        return collisionDetector.isColliding(this, targetCoordinates, currentCollidableObjects);
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

    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getMaxHp() {
        return MAX_HP;
    }
}
