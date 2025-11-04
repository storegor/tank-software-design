package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public interface GameUnitModel {
    void update(float deltaTime, List<? extends GameObject> collidableObjects);
    void setDirection(Direction direction);
    Direction getCurrentDirection();
    GridPoint2 getCoordinates();
    float getRotation();
    GridPoint2 getDestinationCoordinates();
    float getMovementProgress();
}


