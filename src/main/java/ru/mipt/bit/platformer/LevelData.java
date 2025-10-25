package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class LevelData {
    private final GridPoint2 playerStart;
    private final List<GridPoint2> obstaclePositions;

    public LevelData(GridPoint2 playerStart, List<GridPoint2> obstaclePositions) {
        this.playerStart = playerStart;
        this.obstaclePositions = obstaclePositions;
    }

    public GridPoint2 getPlayerStart() {
        return playerStart;
    }

    public List<GridPoint2> getObstaclePositions() {
        return obstaclePositions;
    }
}
