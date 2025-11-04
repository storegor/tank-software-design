package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.Gdx.gl;

public class GameDesktopLauncher implements ApplicationListener {

    private static final String CONFIG_FILE_PATH = "config.properties";

    private Batch batch;
    private GameWorld gameWorld;

    @Override
    public void create() {
        batch = new SpriteBatch();

        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            if (input == null) {
                System.err.println("Sorry, unable to find " + CONFIG_FILE_PATH + ", using default random level generator.");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error loading " + CONFIG_FILE_PATH + ", using default random level generator.");
        }

        LevelGenerator levelGenerator;
        String generatorType = properties.getProperty("level.generator.type", "random");

        switch (generatorType) {
            case "file":
                String levelFilePath = properties.getProperty("level.file.path", "level.txt");
                levelGenerator = new FileLevelGenerator(levelFilePath);
                break;
            case "random":
            default:
                levelGenerator = new RandomLevelGenerator();
                break;
        }

        GameWorldFactory gameWorldFactory = new GameWorldFactory();
        gameWorld = gameWorldFactory.createGameWorld(batch, levelGenerator);
        Gdx.input.setInputProcessor(gameWorld.getPlayerTank().getInputHandler());
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
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
