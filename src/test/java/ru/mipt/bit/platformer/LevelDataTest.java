package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevelDataTest {

    @Test
    void getPlayerStartReturnsCorrectPosition() {
        GridPoint2 playerStart = new GridPoint2(1, 1);
        LevelData levelData = new LevelData(playerStart, Collections.emptyList(), Collections.emptyList());
        assertEquals(playerStart, levelData.getPlayerStart());
    }

    @Test
    void getObstaclePositionsReturnsCorrectList() {
        List<GridPoint2> obstaclePositions = Arrays.asList(new GridPoint2(0, 0), new GridPoint2(1, 0));
        LevelData levelData = new LevelData(new GridPoint2(1, 1), obstaclePositions, Collections.emptyList());
        assertEquals(obstaclePositions, levelData.getObstaclePositions());
    }

    @Test
    void getAiTankPositionsReturnsCorrectList() {
        List<GridPoint2> aiTankPositions = Arrays.asList(new GridPoint2(2, 2), new GridPoint2(3, 3));
        LevelData levelData = new LevelData(new GridPoint2(1, 1), Collections.emptyList(), aiTankPositions);
        assertEquals(aiTankPositions, levelData.getAiTankPositions());
    }
}
