package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TileCollisionDetectorTest {

    @Mock
    private TiledMapTileLayer mockGroundLayer;

    @BeforeEach
    void setUp() {
        mockGroundLayer = Mockito.mock(TiledMapTileLayer.class);
        when(mockGroundLayer.getWidth()).thenReturn(10); 
        when(mockGroundLayer.getHeight()).thenReturn(10); 
    }

    @Test
    void testNoCollisionWithEmptyList() {
        TileCollisionDetector detector = new TileCollisionDetector(mockGroundLayer);
        GridPoint2 target = new GridPoint2(0, 0);
        List<GameObject> collidableObjects = new ArrayList<>();
        assertFalse(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testNoCollisionWithOtherObjects() {
        TileCollisionDetector detector = new TileCollisionDetector(mockGroundLayer);
        GridPoint2 target = new GridPoint2(0, 0);
        List<GameObject> collidableObjects = new ArrayList<>();

        GameUnitModel mockObstacleModel = mock(GameUnitModel.class);
        when(mockObstacleModel.getCoordinates()).thenReturn(new GridPoint2(1, 1));
        GameUnitGraphics mockObstacleGraphics = mock(GameUnitGraphics.class);

        collidableObjects.add(new Obstacle(mockObstacleModel, mockObstacleGraphics));
        assertFalse(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithObstacleAtTarget() {
        TileCollisionDetector detector = new TileCollisionDetector(mockGroundLayer);
        GridPoint2 target = new GridPoint2(1, 1);
        List<GameObject> collidableObjects = new ArrayList<>();

        GameUnitModel mockObstacleModel = mock(GameUnitModel.class);
        when(mockObstacleModel.getCoordinates()).thenReturn(new GridPoint2(1, 1));
        GameUnitGraphics mockObstacleGraphics = mock(GameUnitGraphics.class);

        collidableObjects.add(new Obstacle(mockObstacleModel, mockObstacleGraphics));
        assertTrue(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithMultipleObstacles() {
        TileCollisionDetector detector = new TileCollisionDetector(mockGroundLayer);
        GridPoint2 target = new GridPoint2(2, 2);
        List<GameObject> collidableObjects = new ArrayList<>();

        GameUnitModel mockObstacleModel1 = mock(GameUnitModel.class);
        when(mockObstacleModel1.getCoordinates()).thenReturn(new GridPoint2(1, 1));
        GameUnitGraphics mockObstacleGraphics1 = mock(GameUnitGraphics.class);
        collidableObjects.add(new Obstacle(mockObstacleModel1, mockObstacleGraphics1));

        GameUnitModel mockObstacleModel2 = mock(GameUnitModel.class);
        when(mockObstacleModel2.getCoordinates()).thenReturn(new GridPoint2(2, 2));
        GameUnitGraphics mockObstacleGraphics2 = mock(GameUnitGraphics.class);
        collidableObjects.add(new Obstacle(mockObstacleModel2, mockObstacleGraphics2));

        GameUnitModel mockObstacleModel3 = mock(GameUnitModel.class);
        when(mockObstacleModel3.getCoordinates()).thenReturn(new GridPoint2(3, 3));
        GameUnitGraphics mockObstacleGraphics3 = mock(GameUnitGraphics.class);
        collidableObjects.add(new Obstacle(mockObstacleModel3, mockObstacleGraphics3));

        assertTrue(detector.isColliding(target, collidableObjects));
    }

    @Test
    void testCollisionWithOutOfBoundsTile() {
        TileCollisionDetector detector = new TileCollisionDetector(mockGroundLayer);
        GridPoint2 targetOutOfBoundsX = new GridPoint2(-1, 0);
        assertTrue(detector.isColliding(targetOutOfBoundsX, new ArrayList<>()));

        GridPoint2 targetOutOfBoundsY = new GridPoint2(0, -1);
        assertTrue(detector.isColliding(targetOutOfBoundsY, new ArrayList<>()));

        GridPoint2 targetOutOfBoundsGreaterX = new GridPoint2(10, 0);
        assertTrue(detector.isColliding(targetOutOfBoundsGreaterX, new ArrayList<>()));

        GridPoint2 targetOutOfBoundsGreaterY = new GridPoint2(0, 10);
        assertTrue(detector.isColliding(targetOutOfBoundsGreaterY, new ArrayList<>()));
    }
}
