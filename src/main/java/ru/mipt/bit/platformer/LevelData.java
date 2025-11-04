package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class LevelData {
    private final GridPoint2 playerStart;
    private final List<GridPoint2> obstaclePositions;
    private final List<GridPoint2> aiTankPositions;

    public LevelData(GridPoint2 playerStart, List<GridPoint2> obstaclePositions, List<GridPoint2> aiTankPositions) {
        this.playerStart = playerStart;
        this.obstaclePositions = obstaclePositions;
        this.aiTankPositions = aiTankPositions;
    }

    public GridPoint2 getPlayerStart() {
        return playerStart;
    }

    public List<GridPoint2> getObstaclePositions() {
        return obstaclePositions;
    }

    public List<GridPoint2> getAiTankPositions() {
        return aiTankPositions;
    }
}
