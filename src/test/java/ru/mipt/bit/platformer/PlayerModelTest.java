package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerModelTest {

    @Mock
    private TiledMapTileLayer mockGroundLayer;
    @Mock
    private Interpolation mockInterpolation;
    @Mock
    private CollisionDetector mockCollisionDetector;
    @Mock
    private TileMovement mockTileMovement;

    private PlayerModel playerModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the behavior of TileMovement if needed in specific tests
        // The PlayerModel now takes TileMovement as a dependency.

        playerModel = new PlayerModel(new GridPoint2(0, 0), mockCollisionDetector, mockTileMovement);
    }

    @Test
    void testInitialPositionAndRotation() {
        assertEquals(new GridPoint2(0, 0), playerModel.getCoordinates());
        assertEquals(0f, playerModel.getRotation());
    }

    @Test
    void testMovementWithoutCollision() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(false);
        playerModel.update(0.1f, Collections.emptyList(), Direction.RIGHT);
        playerModel.update(0.1f, Collections.emptyList(), null);
        playerModel.update(0.1f, Collections.emptyList(), null);
        playerModel.update(0.1f, Collections.emptyList(), null);
        assertEquals(new GridPoint2(1, 0), playerModel.getCoordinates());
        assertEquals(0f, playerModel.getRotation());
    }

    @Test
    void testMovementWithCollision() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(true);
        playerModel.update(0.1f, Collections.emptyList(), Direction.RIGHT);
        assertEquals(new GridPoint2(0, 0), playerModel.getCoordinates()); // Should not move due to collision
        assertEquals(0f, playerModel.getRotation());
    }

    @Test
    void testRotationChange() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(false);
        playerModel.update(0.1f, Collections.emptyList(), Direction.UP);
        assertEquals(new GridPoint2(0, 1), playerModel.getDestinationCoordinates());
        assertEquals(90f, playerModel.getRotation());
    }

    @Test
    void testMovementProgressUpdate() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(false);
        playerModel.update(0.1f, Collections.emptyList(), Direction.UP);
        assertEquals(0f, playerModel.getMovementProgress()); // Should reset to 0 at start of movement
        playerModel.update(0.1f, Collections.emptyList(), null); // Simulate one step of movement
        // The exact value depends on MOVEMENT_SPEED, but it should be > 0 and < 1
        assertTrue(playerModel.getMovementProgress() > 0f && playerModel.getMovementProgress() < 1f);
    }

    @Test
    void testDestinationReached() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(false);
        playerModel.update(0.1f, Collections.emptyList(), Direction.RIGHT);
        // Simulate full movement progress
        for (int i = 0; i < 4; i++) {
            playerModel.update(0.1f, Collections.emptyList(), null);
        }
        assertEquals(new GridPoint2(1, 0), playerModel.getCoordinates());
        assertEquals(1f, playerModel.getMovementProgress());
    }

    @Test
    void testTileMovementInteraction() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(false);
        playerModel = new PlayerModel(new GridPoint2(0, 0), mockCollisionDetector, mockTileMovement);
        playerModel.update(0.1f, Collections.emptyList(), Direction.RIGHT);

        verify(mockTileMovement).moveRectangleBetweenTileCenters(null, new GridPoint2(0, 0), new GridPoint2(1, 0), 0f);
    }
}
