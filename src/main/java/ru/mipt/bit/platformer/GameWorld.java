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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameWorld implements Disposable {
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;
    private final Player player;
    private final Obstacle treeObstacle;
    private final List<Obstacle> obstacles;
    private final CollisionDetector collisionDetector;
    private final KeyboardInputHandler inputHandler;

    public GameWorld(Batch batch) {
        // Загрузка тайлов уровня
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);

        // Инициализация игровых объектов
        gameObjects = new ArrayList<>();
        obstacles = new ArrayList<>();
        collisionDetector = new TileCollisionDetector();
        inputHandler = new KeyboardInputHandler();

        Texture greenTreeTexture = new Texture("images/greenTree.png");
        treeObstacle = new Obstacle(greenTreeTexture, new GridPoint2(1, 3), groundLayer);
        obstacles.add(treeObstacle);
        gameObjects.add(treeObstacle);

        Texture blueTankTexture = new Texture("images/tank_blue.png");
        player = new Player(blueTankTexture, new GridPoint2(1, 1), groundLayer, Interpolation.smooth, collisionDetector, inputHandler);
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
            if (object instanceof AbstractGameObject) {
                ((AbstractGameObject) object).dispose();
            } else if (object instanceof Obstacle) {
                ((Obstacle) object).dispose();
            } else if (object instanceof Player) {
                ((Player) object).dispose();
            }
        }
    }
} 