package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameWorld implements Disposable {
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;
    private final Player player;
    private final List<Obstacle> obstacles;
    private final CollisionDetector collisionDetector;
    private final InputHandler inputHandler;

    public GameWorld(
            Batch batch,
            String levelPath,
            String greenTreeTexturePath,
            String blueTankTexturePath,
            float playerMovementSpeed,
            CollisionDetector collisionDetector,
            InputHandler inputHandler,
            LevelGenerator levelGenerator) {
        level = new TmxMapLoader().load(levelPath);
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);

        LevelData levelData = levelGenerator.generateLevel(groundLayer);

        gameObjects = new ArrayList<>();
        obstacles = new ArrayList<>();
        this.collisionDetector = collisionDetector;
        this.inputHandler = inputHandler;

        Texture greenTreeTexture = new Texture(greenTreeTexturePath);
        for (GridPoint2 obstaclePos : levelData.getObstaclePositions()) {
            ObstacleModel treeModel = new ObstacleModel(obstaclePos);
            ObstacleGraphics treeGraphics = new ObstacleGraphics(greenTreeTexture, treeModel.getCoordinates(), groundLayer);
            Obstacle treeObstacle = new Obstacle(treeModel, treeGraphics);
            obstacles.add(treeObstacle);
            gameObjects.add(treeObstacle);
        }

        Texture blueTankTexture = new Texture(blueTankTexturePath);
        PlayerModel playerModel = new PlayerModel(levelData.getPlayerStart(), collisionDetector, new TileMovement(groundLayer, Interpolation.smooth), playerMovementSpeed);
        PlayerGraphics playerGraphics = new PlayerGraphics(blueTankTexture, playerModel.getCoordinates(), playerModel.getRotation(), groundLayer, Interpolation.smooth);
        player = new Player(playerModel, playerGraphics, inputHandler);
        gameObjects.add(player);

        Gdx.input.setInputProcessor(inputHandler);
    }

    public void update(float deltaTime) {
        List<? extends GameObject> collidableObjects = getCollidableObjects();
        for (GameObject object : gameObjects) {
            object.update(deltaTime, collidableObjects);
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

    public Player getPlayer() {
        return player;
    }

    private List<? extends GameObject> getCollidableObjects() {
        List<GameObject> allCollidable = new ArrayList<>(obstacles);
        return allCollidable;
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