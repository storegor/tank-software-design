package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class BulletModel implements GameUnitModel, OwnedProjectile {
    private static final float TILE_SIZE = 48f;
    private static final float BULLET_SPEED = 300f;
    private static final int BULLET_DAMAGE = 10;
    private static final float INITIAL_PROGRESS = 0.5f;
    
    private final GridPoint2 coordinates;
    private final Direction direction;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = INITIAL_PROGRESS;
    private final float movementSpeed = BULLET_SPEED;
    private final CollisionDetector collisionDetector;
    private final int damage = BULLET_DAMAGE;
    private final GameUnitModel owner;

    public BulletModel(GridPoint2 startCoordinates, Direction direction, CollisionDetector collisionDetector, GameUnitModel owner) {
        this.coordinates = new GridPoint2(startCoordinates);
        this.destinationCoordinates = new GridPoint2(startCoordinates).add(direction.getVector());
        this.direction = direction;
        this.collisionDetector = collisionDetector;
        this.owner = owner;
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        float distance = movementSpeed * deltaTime;
        movementProgress += distance / TILE_SIZE;

        if (movementProgress >= 1f) {
            coordinates.set(destinationCoordinates);
            destinationCoordinates.add(direction.getVector());
            movementProgress = 0f;
        }
    }

    @Override
    public void setDirection(Direction direction) {
    }

    @Override
    public Direction getCurrentDirection() {
        return direction;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public GameUnitModel getOwner() {
        return owner;
    }

    @Override
    public void takeDamage(int damage) {
    }
}

