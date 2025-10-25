package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLevelGenerator implements LevelGenerator {
    private final Random random;

    public RandomLevelGenerator() {
        this.random = new Random();
    }

    @Override
    public LevelData generateLevel(TiledMapTileLayer groundLayer) {
        int mapWidth = groundLayer.getWidth();
        int mapHeight = groundLayer.getHeight();

        GridPoint2 playerStart = new GridPoint2(random.nextInt(mapWidth), random.nextInt(mapHeight));
        List<GridPoint2> obstaclePositions = new ArrayList<>();

        int numberOfObstacles = random.nextInt(mapWidth * mapHeight / 5) + 1;

        for (int i = 0; i < numberOfObstacles; i++) {
            GridPoint2 obstaclePos;
            do {
                obstaclePos = new GridPoint2(random.nextInt(mapWidth), random.nextInt(mapHeight));
            } while (obstaclePos.equals(playerStart) || obstaclePositions.contains(obstaclePos));
            obstaclePositions.add(obstaclePos);
        }

        return new LevelData(playerStart, obstaclePositions);
    }
}
