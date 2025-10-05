package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerModelTest {

    @Mock
    private CollisionDetector mockCollisionDetector;
    @Mock
    private TileMovement mockTileMovement;

    private PlayerModel playerModel;

    @BeforeEach
    void setUp() {
        mockCollisionDetector = mock(CollisionDetector.class);
        mockTileMovement = mock(TileMovement.class);

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
        assertEquals(new GridPoint2(0, 0), playerModel.getCoordinates()); // Не должен двигаться из-за столкновения
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
        assertEquals(0f, playerModel.getMovementProgress()); // Должен сброситься до 0 в начале движения
        playerModel.update(0.1f, Collections.emptyList(), null); // Имитация одного шага движения
        // Точное значение зависит от MOVEMENT_SPEED, но оно должно быть > 0 и < 1
        assertTrue(playerModel.getMovementProgress() > 0f && playerModel.getMovementProgress() < 1f);
    }

    @Test
    void testDestinationReached() {
        when(mockCollisionDetector.isColliding(any(), any())).thenReturn(false);
        playerModel.update(0.1f, Collections.emptyList(), Direction.RIGHT);
        // Имитация полного прогресса движения
        for (int i = 0; i < 4; i++) {
            playerModel.update(0.1f, Collections.emptyList(), null);
        }
        assertEquals(new GridPoint2(1, 0), playerModel.getCoordinates());
        assertEquals(1f, playerModel.getMovementProgress());
    }

}
