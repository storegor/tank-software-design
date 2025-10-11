package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.Gdx.gl;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final String LEVEL_PATH = "level.tmx";
    private static final String GREEN_TREE_TEXTURE_PATH = "images/greenTree.png";
    private static final String BLUE_TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final float PLAYER_MOVEMENT_SPEED = 0.4f;

    private Batch batch;
    private GameWorld gameWorld;
    private TiledMap level;
    private TiledMapTileLayer groundLayer;
    private CollisionDetector collisionDetector;
    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        level = new TmxMapLoader().load(LEVEL_PATH);
        groundLayer = getSingleLayer(level);
        collisionDetector = new TileCollisionDetector(groundLayer);
        inputHandler = new KeyboardInputHandler();
        gameWorld = new GameWorld(
                batch,
                LEVEL_PATH,
                GREEN_TREE_TEXTURE_PATH,
                BLUE_TANK_TEXTURE_PATH,
                PLAYER_MOVEMENT_SPEED,
                collisionDetector,
                inputHandler);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render() {
        gl.glClearColor(0f, 0f, 0.2f, 1f);
        gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        gameWorld.update(deltaTime);
        gameWorld.render(batch);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gameWorld.dispose();
        batch.dispose();
        level.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
