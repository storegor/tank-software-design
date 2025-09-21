package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class TileCollisionDetector implements CollisionDetector {
    @Override
    public boolean isColliding(GridPoint2 targetCoordinates, List<? extends AbstractGameObject> collidableObjects) {
        for (AbstractGameObject object : collidableObjects) {
            if (object.getCoordinates().equals(targetCoordinates)) {
                return true;
            }
        }
        return false;
    }
} 
