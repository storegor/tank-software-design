package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class PlayerModelTest {

    private static final float PLAYER_MOVEMENT_SPEED = 0.4f;

    @Mock
    private CollisionDetector collisionDetector;
    @Mock
    private TiledMapTileLayer groundLayer;

    private PlayerModel playerModel;
    private GridPoint2 initialCoordinates;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initialCoordinates = new GridPoint2(1, 1);
        playerModel = new PlayerModel(initialCoordinates, collisionDetector, PLAYER_MOVEMENT_SPEED);

        when(groundLayer.getWidth()).thenReturn(10);
        when(groundLayer.getHeight()).thenReturn(10);
    }

    @Test
    void initialCoordinatesAreCorrect() {
        assertEquals(initialCoordinates, playerModel.getCoordinates());
    }

    @Test
    void setDirectionUpdatesCurrentDirection() {
        playerModel.setDirection(Direction.UP);
        playerModel.update(0f, Collections.emptyList());
        assertEquals(new GridPoint2(1, 2), playerModel.getDestinationCoordinates());
    }

    @Test
    void movementUpdatesCoordinates() {
        when(collisionDetector.isColliding(eq(playerModel), any(GridPoint2.class), anyList())).thenReturn(false);
        playerModel.setDirection(Direction.RIGHT);
        playerModel.update(1f, Collections.emptyList());
        assertEquals(new GridPoint2(2, 1), playerModel.getCoordinates());
    }

    @Test
    void collisionPreventsMovement() {
        when(collisionDetector.isColliding(eq(playerModel), any(GridPoint2.class), anyList())).thenReturn(true);
        playerModel.setDirection(Direction.UP);
        playerModel.update(1f, Collections.emptyList());
        assertEquals(initialCoordinates, playerModel.getCoordinates());
    }

    @Test
    void rotationUpdatesOnMovement() {
        when(collisionDetector.isColliding(eq(playerModel), any(GridPoint2.class), anyList())).thenReturn(false);
        playerModel.setDirection(Direction.UP);
        playerModel.update(1f, Collections.emptyList());
        assertEquals(Direction.UP.getRotation(), playerModel.getRotation());
    }

    @Test
    void playerModelStaysInBounds() {
        // Simulate movement to the right edge
        when(collisionDetector.isColliding(eq(playerModel), any(GridPoint2.class), anyList())).thenReturn(false);
        for (int i = 0; i < 9; i++) { // Move to (9,1)
            playerModel.setDirection(Direction.RIGHT);
            playerModel.update(1f, Collections.emptyList());
        }
        assertEquals(new GridPoint2(9, 1), playerModel.getCoordinates());

        // Try to move out of bounds (right)
        when(collisionDetector.isColliding(eq(playerModel), eq(new GridPoint2(10, 1)), anyList())).thenReturn(true);
        playerModel.setDirection(Direction.RIGHT);
        playerModel.update(1f, Collections.emptyList());
        assertEquals(new GridPoint2(9, 1), playerModel.getCoordinates());
    }
}
