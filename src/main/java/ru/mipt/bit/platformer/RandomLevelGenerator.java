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

        List<GridPoint2> occupiedPositions = new ArrayList<>();

        GridPoint2 playerStart = findRandomEmptyPosition(mapWidth, mapHeight, occupiedPositions);
        occupiedPositions.add(playerStart);

        List<GridPoint2> obstaclePositions = new ArrayList<>();
        int numberOfObstacles = random.nextInt(mapWidth * mapHeight / 5) + 1;
        for (int i = 0; i < numberOfObstacles; i++) {
            GridPoint2 obstaclePos = findRandomEmptyPosition(mapWidth, mapHeight, occupiedPositions);
            obstaclePositions.add(obstaclePos);
            occupiedPositions.add(obstaclePos);
        }

        List<GridPoint2> aiTankPositions = new ArrayList<>();
        int numberOfAiTanks = 3;
        for (int i = 0; i < numberOfAiTanks; i++) {
            GridPoint2 aiTankPos = findRandomEmptyPosition(mapWidth, mapHeight, occupiedPositions);
            aiTankPositions.add(aiTankPos);
            occupiedPositions.add(aiTankPos);
        }

        return new LevelData(playerStart, obstaclePositions, aiTankPositions);
    }

    private GridPoint2 findRandomEmptyPosition(int mapWidth, int mapHeight, List<GridPoint2> occupiedPositions) {
        GridPoint2 position;
        do {
            position = new GridPoint2(random.nextInt(mapWidth), random.nextInt(mapHeight));
        } while (occupiedPositions.contains(position));
        return position;
    }
}
