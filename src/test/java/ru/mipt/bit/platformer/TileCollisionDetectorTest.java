package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class TileCollisionDetectorTest {

    @Mock
    private TiledMapTileLayer groundLayer;
    @Mock
    private ObstacleModel mockObstacleModel1;
    @Mock
    private ObstacleGraphics mockObstacleGraphics1;
    @Mock
    private ObstacleModel mockObstacleModel2;
    @Mock
    private ObstacleGraphics mockObstacleGraphics2;

    private TileCollisionDetector collisionDetector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(groundLayer.getWidth()).thenReturn(10);
        when(groundLayer.getHeight()).thenReturn(10);
        collisionDetector = new TileCollisionDetector(groundLayer);
        
        when(mockObstacleGraphics1.getRectangle()).thenReturn(new Rectangle(0,0,1,1));
        when(mockObstacleGraphics2.getRectangle()).thenReturn(new Rectangle(0,0,1,1));

        when(mockObstacleModel1.getCoordinates()).thenReturn(new GridPoint2(0,0));
        when(mockObstacleModel2.getCoordinates()).thenReturn(new GridPoint2(0,0));
    }

    @Test
    void isCollidingReturnsTrueWhenOutOfBounds() {
        GridPoint2 outOfBounds = new GridPoint2(-1, 0);
        assertTrue(collisionDetector.isColliding(mock(GameUnitModel.class), outOfBounds, Collections.emptyList()));

        outOfBounds = new GridPoint2(10, 0);
        assertTrue(collisionDetector.isColliding(mock(GameUnitModel.class), outOfBounds, Collections.emptyList()));

        outOfBounds = new GridPoint2(0, -1);
        assertTrue(collisionDetector.isColliding(mock(GameUnitModel.class), outOfBounds, Collections.emptyList()));

        outOfBounds = new GridPoint2(0, 10);
        assertTrue(collisionDetector.isColliding(mock(GameUnitModel.class), outOfBounds, Collections.emptyList()));
    }

    @Test
    void isCollidingReturnsTrueWhenCollidingWithGameObject() {
        GridPoint2 obstaclePos = new GridPoint2(5, 5);
        when(mockObstacleModel1.getCoordinates()).thenReturn(obstaclePos);
        Obstacle obstacle = new Obstacle(mockObstacleModel1, mockObstacleGraphics1);

        List<GameObject> collidableObjects = Collections.singletonList(obstacle);
        assertTrue(collisionDetector.isColliding(mock(GameUnitModel.class), obstaclePos, collidableObjects));
    }

    @Test
    void isCollidingReturnsFalseWhenNotColliding() {
        GridPoint2 targetPos = new GridPoint2(5, 5);
        GridPoint2 obstaclePos = new GridPoint2(6, 6);
        when(mockObstacleModel1.getCoordinates()).thenReturn(obstaclePos);
        Obstacle obstacle = new Obstacle(mockObstacleModel1, mockObstacleGraphics1);

        List<GameObject> collidableObjects = Collections.singletonList(obstacle);
        assertFalse(collisionDetector.isColliding(mock(GameUnitModel.class), targetPos, collidableObjects));
    }

    @Test
    void isCollidingReturnsFalseWhenInBoundsAndNoCollision() {
        GridPoint2 targetPos = new GridPoint2(5, 5);
        assertFalse(collisionDetector.isColliding(mock(GameUnitModel.class), targetPos, Collections.emptyList()));
    }

    @Test
    void isCollidingChecksAllCollidableObjects() {
        GridPoint2 targetPos = new GridPoint2(5, 5);
        when(mockObstacleModel1.getCoordinates()).thenReturn(new GridPoint2(1, 1));
        when(mockObstacleModel2.getCoordinates()).thenReturn(targetPos);

        Obstacle obstacle1 = new Obstacle(mockObstacleModel1, mockObstacleGraphics1);
        Obstacle obstacle2 = new Obstacle(mockObstacleModel2, mockObstacleGraphics2);

        List<GameObject> collidableObjects = Arrays.asList(obstacle1, obstacle2);
        assertTrue(collisionDetector.isColliding(mock(GameUnitModel.class), targetPos, collidableObjects));
    }
}
