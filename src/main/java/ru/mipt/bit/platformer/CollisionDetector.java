package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public abstract class CollisionDetector {
    protected final TiledMapTileLayer groundLayer;

    public CollisionDetector(TiledMapTileLayer groundLayer) {
        this.groundLayer = groundLayer;
    }

    public abstract boolean isColliding(GridPoint2 targetCoordinates, List<? extends GameObject> collidableObjects);

    protected boolean isTileOutOfBounds(GridPoint2 targetCoordinates) {
        int mapWidthInTiles = groundLayer.getWidth();
        int mapHeightInTiles = groundLayer.getHeight();
        return targetCoordinates.x < 0 || targetCoordinates.x >= mapWidthInTiles ||
               targetCoordinates.y < 0 || targetCoordinates.y >= mapHeightInTiles;
    }
} 