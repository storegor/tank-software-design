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
    public final Batch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final List<GameObject> gameObjects;
    private final PlayerTank playerTank;
    private final List<Obstacle> obstacles;
    private final List<Tank> aiTanks;
    private final CollisionDetector collisionDetector;
    private final List<HealthBarGraphicsDecorator> healthBarDecorators;
    private final List<GameWorldListener> listeners;
    private final List<Bullet> bullets;
    private final List<Disposable> sharedResources;

    public GameWorld(
            Batch batch,
            TiledMap level,
            CollisionDetector collisionDetector,
            PlayerTank playerTank,
            List<Tank> aiTanks,
            List<Obstacle> obstacles,
            List<HealthBarGraphicsDecorator> healthBarDecorators) {
        this.batch = batch;
        this.level = level;
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);

        this.playerTank = playerTank;
        this.aiTanks = aiTanks;
        this.collisionDetector = collisionDetector;
        this.healthBarDecorators = healthBarDecorators;
        this.listeners = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.sharedResources = new ArrayList<>();

        this.obstacles = obstacles;
        gameObjects = new ArrayList<>();
        gameObjects.addAll(obstacles);

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

        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Tank> tanksToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            boolean bulletHit = false;
            GameUnitModel bulletOwner = bullet.getOwner();
            
            for (Obstacle obstacle : obstacles) {
                if (bullet.getRectangle().overlaps(obstacle.getRectangle())) {
                    bulletsToRemove.add(bullet);
                    bulletHit = true;
                    break;
                }
            }
            if (!bulletHit) {
                if (playerTank.getModel() != bulletOwner && bullet.getRectangle().overlaps(playerTank.getRectangle())) {
                    playerTank.getModel().takeDamage(bullet.getDamage());
                    bulletsToRemove.add(bullet);
                    if (playerTank.getModel().getHp() <= 0) {
                        tanksToRemove.add(playerTank);
                    }
                } else {
                    for (Tank aiTank : aiTanks) {
                        if (aiTank.getModel() != bulletOwner && bullet.getRectangle().overlaps(aiTank.getRectangle())) {
                            aiTank.getModel().takeDamage(bullet.getDamage());
                            bulletsToRemove.add(bullet);
                            if (aiTank.getModel().getHp() <= 0) {
                                tanksToRemove.add(aiTank);
                            }
                            break;
                        }
                    }
                }
            }
        }

        for (Bullet bullet : bulletsToRemove) {
            removeBullet(bullet);
        }

        for (Tank tank : tanksToRemove) {
            removeTank(tank);
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

    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public void addSharedResource(Disposable resource) {
        sharedResources.add(resource);
    }

    public void initializeAiControllers() {
        for (Tank aiTank : aiTanks) {
            AiTankController controller = aiTank.getAiTankController();
            if (controller != null) {
                controller.setGameWorld(this);
            }
        }
    }

    public void addListener(GameWorldListener listener) {
        listeners.add(listener);
        for (GameObject obj : gameObjects) {
            listener.onGameObjectAdded(obj);
        }
    }

    public void removeListener(GameWorldListener listener) {
        listeners.remove(listener);
    }

    private void notifyObjectAdded(GameObject gameObject) {
        for (GameWorldListener listener : listeners) {
            listener.onGameObjectAdded(gameObject);
        }
    }

    private void notifyObjectRemoved(GameObject gameObject) {
        for (GameWorldListener listener : listeners) {
            listener.onGameObjectRemoved(gameObject);
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        gameObjects.add(bullet);
        notifyObjectAdded(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        gameObjects.remove(bullet);
        notifyObjectRemoved(bullet);
        bullet.dispose();
    }

    public void removeTank(Tank tank) {
        aiTanks.remove(tank);
        gameObjects.remove(tank);
        notifyObjectRemoved(tank);
        tank.dispose();
    }

    @Override
    public void dispose() {
        level.dispose();
        for (GameObject object : gameObjects) {
            if (object instanceof Disposable) {
                ((Disposable) object).dispose();
            }
        }
        for (Disposable resource : sharedResources) {
            resource.dispose();
        }
    }
} 