package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class TileCollisionDetector extends CollisionDetector {

    public TileCollisionDetector(TiledMapTileLayer groundLayer) {
        super(groundLayer);
    }

    @Override
    public boolean isColliding(GridPoint2 targetCoordinates, List<? extends GameObject> collidableObjects) {
        if (isTileOutOfBounds(targetCoordinates)) {
            return true;
        }
        for (GameObject object : collidableObjects) {
            if (object.getCoordinates().equals(targetCoordinates)) {
                return true;
            }
        }
        return false;
    }
} 
