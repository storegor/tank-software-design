package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileCollisionDetectorTest {

    @Mock
    private TiledMapTileLayer mockGroundLayer;

    @Test
    void testNoCollisionWithEmptyList() {
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(0, 0);
        List<GameObject> collidableObjects = new ArrayList<>();
        assertFalse(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testNoCollisionWithOtherObjects() {
        mockGroundLayer = Mockito.mock(TiledMapTileLayer.class);
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(0, 0);
        List<GameObject> collidableObjects = new ArrayList<>();
        collidableObjects.add(new Obstacle(null, new GridPoint2(1, 1), mockGroundLayer));
        assertFalse(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithObstacleAtTarget() {
        mockGroundLayer = Mockito.mock(TiledMapTileLayer.class);
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(1, 1);
        List<GameObject> collidableObjects = new ArrayList<>();
        collidableObjects.add(new Obstacle(null, new GridPoint2(1, 1), mockGroundLayer));
        assertTrue(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithMultipleObstacles() {
        mockGroundLayer = Mockito.mock(TiledMapTileLayer.class);
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(2, 2);
        List<GameObject> collidableObjects = new ArrayList<>();
        collidableObjects.add(new Obstacle(null, new GridPoint2(1, 1), mockGroundLayer));
        collidableObjects.add(new Obstacle(null, new GridPoint2(2, 2), mockGroundLayer));
        collidableObjects.add(new Obstacle(null, new GridPoint2(3, 3), mockGroundLayer));
        assertTrue(detector.isColliding(target, collidableObjects));
    }
}
