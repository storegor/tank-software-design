package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileCollisionDetectorTest {

    @Test
    void testNoCollisionWithEmptyList() {
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(0, 0);
        List<AbstractGameObject> collidableObjects = new ArrayList<>();
        assertFalse(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testNoCollisionWithOtherObjects() {
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(0, 0);
        List<AbstractGameObject> collidableObjects = new ArrayList<>();
        collidableObjects.add(new Obstacle(null, new GridPoint2(1, 1))); // null texture is okay for this test
        assertFalse(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithObstacleAtTarget() {
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(1, 1);
        List<AbstractGameObject> collidableObjects = new ArrayList<>();
        collidableObjects.add(new Obstacle(null, new GridPoint2(1, 1)));
        assertTrue(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithMultipleObstacles() {
        TileCollisionDetector detector = new TileCollisionDetector();
        GridPoint2 target = new GridPoint2(2, 2);
        List<AbstractGameObject> collidableObjects = new ArrayList<>();
        collidableObjects.add(new Obstacle(null, new GridPoint2(1, 1)));
        collidableObjects.add(new Obstacle(null, new GridPoint2(2, 2)));
        collidableObjects.add(new Obstacle(null, new GridPoint2(3, 3)));
        assertTrue(detector.isColliding(target, collidableObjects));
    }
}
