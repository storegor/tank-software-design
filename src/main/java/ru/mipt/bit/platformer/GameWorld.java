package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameWorld implements Disposable {
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;
    private final PlayerTank playerTank;
    private final List<Obstacle> obstacles;
    private final List<Tank> aiTanks;
    private final CollisionDetector collisionDetector;
    private final List<HealthBarGraphicsDecorator> healthBarDecorators;

    public GameWorld(
            Batch batch,
            String levelPath,
            String greenTreeTexturePath,
            float playerMovementSpeed,
            CollisionDetector collisionDetector,
            LevelData levelData,
            PlayerTank playerTank,
            List<Tank> aiTanks,
            List<HealthBarGraphicsDecorator> healthBarDecorators) {
        level = new TmxMapLoader().load(levelPath);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);

        this.playerTank = playerTank;
        this.aiTanks = aiTanks;
        this.collisionDetector = collisionDetector;
        this.healthBarDecorators = healthBarDecorators;

        gameObjects = new ArrayList<>();
        obstacles = new ArrayList<>();

        Texture greenTreeTexture = new Texture(greenTreeTexturePath);
        for (GridPoint2 obstaclePos : levelData.getObstaclePositions()) {
            ObstacleModel treeModel = new ObstacleModel(obstaclePos);
            ObstacleGraphics treeGraphics = new ObstacleGraphics(greenTreeTexture, treeModel.getCoordinates(), groundLayer);
            Obstacle treeObstacle = new Obstacle(treeModel, treeGraphics);
            obstacles.add(treeObstacle);
            gameObjects.add(treeObstacle);
        }

        gameObjects.add(playerTank);
        gameObjects.addAll(aiTanks);
    }

    public void update(float deltaTime) {
        List<GameObject> allCollidableObjects = new ArrayList<>();
        allCollidableObjects.addAll(obstacles);
        allCollidableObjects.add(playerTank);
        allCollidableObjects.addAll(aiTanks);

        playerTank.update(deltaTime, allCollidableObjects);
        for (Tank aiTank : aiTanks) {
            aiTank.update(deltaTime, allCollidableObjects);
        }

        for (GameObject object : gameObjects) {
            if (!(object instanceof Tank)) {
                object.update(deltaTime, allCollidableObjects);
            }
        }
    }

    public void render(Batch batch) {
        levelRenderer.render();
        batch.begin();
        for (GameObject object : gameObjects) {
            object.render(batch);
        }
        batch.end();
    }

    public PlayerTank getPlayerTank() {
        return playerTank;
    }

    public List<HealthBarGraphicsDecorator> getHealthBarDecorators() {
        return healthBarDecorators;
    }

    @Override
    public void dispose() {
        level.dispose();
        for (GameObject object : gameObjects) {
            if (object instanceof Disposable) {
                ((Disposable) object).dispose();
            }
        }
    }
} 