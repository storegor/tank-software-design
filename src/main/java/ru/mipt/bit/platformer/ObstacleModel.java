package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class ObstacleModel implements GameUnitModel {
    private final GridPoint2 coordinates;

    public ObstacleModel(GridPoint2 initialCoordinates) {
        this.coordinates = new GridPoint2(initialCoordinates);
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects, Direction intendedDirection) {
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public float getRotation() {
        return 0f;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return coordinates;
    }

    @Override
    public float getMovementProgress() {
        return 1f;
    }
}
