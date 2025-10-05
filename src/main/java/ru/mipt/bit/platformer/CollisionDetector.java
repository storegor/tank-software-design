package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public interface CollisionDetector {
    boolean isColliding(GridPoint2 targetCoordinates, List<? extends GameObject> collidableObjects);
} 