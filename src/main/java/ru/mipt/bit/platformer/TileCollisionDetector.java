package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class TileCollisionDetector extends CollisionDetector {

    public TileCollisionDetector(TiledMapTileLayer groundLayer) {
        super(groundLayer);
    }

    @Override
    public boolean isColliding(GameUnitModel movingUnit, GridPoint2 targetCoordinates, List<? extends GameObject> collidableObjects) {
        if (isTileOutOfBounds(targetCoordinates)) {
            return true;
        }

        for (GameObject object : collidableObjects) {
            if (object instanceof Tank && ((Tank) object).getModel() == movingUnit) {
                continue;
            }

            if (object instanceof Obstacle) {
                if (object.getCoordinates().equals(targetCoordinates)) {
                    return true;
                }
            }
            else if (object instanceof Tank) {
                Tank otherTank = (Tank) object;
                if (otherTank.getModel().getMovementProgress() < 1f) {
                    if (otherTank.getModel().getCoordinates().equals(targetCoordinates) ||
                        otherTank.getModel().getDestinationCoordinates().equals(targetCoordinates)) {
                        return true;
                    }
                } else {
                    if (otherTank.getModel().getCoordinates().equals(targetCoordinates)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
} 
