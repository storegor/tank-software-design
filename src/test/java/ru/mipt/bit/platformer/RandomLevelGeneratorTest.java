package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RandomLevelGeneratorTest {

    @Mock
    private TiledMapTileLayer groundLayer;

    private RandomLevelGenerator generator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(groundLayer.getWidth()).thenReturn(10);
        when(groundLayer.getHeight()).thenReturn(10);
        generator = new RandomLevelGenerator();
    }

    @Test
    void generateLevelCreatesPlayerStartWithinBounds() {
        LevelData levelData = generator.generateLevel(groundLayer);
        GridPoint2 playerStart = levelData.getPlayerStart();
        assertNotNull(playerStart);
        assertTrue(playerStart.x >= 0 && playerStart.x < 10);
        assertTrue(playerStart.y >= 0 && playerStart.y < 10);
    }

    @Test
    void generateLevelCreatesObstaclesWithinBounds() {
        LevelData levelData = generator.generateLevel(groundLayer);
        List<GridPoint2> obstaclePositions = levelData.getObstaclePositions();
        assertNotNull(obstaclePositions);
        assertFalse(obstaclePositions.isEmpty());
        for (GridPoint2 pos : obstaclePositions) {
            assertTrue(pos.x >= 0 && pos.x < 10);
            assertTrue(pos.y >= 0 && pos.y < 10);
        }
    }

    @Test
    void generateLevelCreatesAiTanksWithinBounds() {
        LevelData levelData = generator.generateLevel(groundLayer);
        List<GridPoint2> aiTankPositions = levelData.getAiTankPositions();
        assertNotNull(aiTankPositions);
        assertFalse(aiTankPositions.isEmpty());
        for (GridPoint2 pos : aiTankPositions) {
            assertTrue(pos.x >= 0 && pos.x < 10);
            assertTrue(pos.y >= 0 && pos.y < 10);
        }
    }

    @Test
    void generateLevelEnsuresNoOverlap() {
        LevelData levelData = generator.generateLevel(groundLayer);
        GridPoint2 playerStart = levelData.getPlayerStart();
        List<GridPoint2> obstaclePositions = levelData.getObstaclePositions();
        List<GridPoint2> aiTankPositions = levelData.getAiTankPositions();

        assertFalse(obstaclePositions.contains(playerStart));
        assertFalse(aiTankPositions.contains(playerStart));

        for (GridPoint2 obstaclePos : obstaclePositions) {
            assertFalse(aiTankPositions.contains(obstaclePos));
        }

        for (int i = 0; i < obstaclePositions.size(); i++) {
            for (int j = i + 1; j < obstaclePositions.size(); j++) {
                assertFalse(obstaclePositions.get(i).equals(obstaclePositions.get(j)));
            }
        }

        for (int i = 0; i < aiTankPositions.size(); i++) {
            for (int j = i + 1; j < aiTankPositions.size(); j++) {
                assertFalse(aiTankPositions.get(i).equals(aiTankPositions.get(j)));
            }
        }
    }
}
