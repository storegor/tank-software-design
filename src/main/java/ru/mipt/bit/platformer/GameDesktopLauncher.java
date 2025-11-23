package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.config.AppConfig;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.Gdx.gl;

public class GameDesktopLauncher implements ApplicationListener {

    private GameWorld gameWorld;
    private GameScreen gameScreen;
    private AnnotationConfigApplicationContext context;

    @Override
    public void create() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        gameWorld = context.getBean(GameWorld.class);
        gameScreen = new GameScreen();
        gameWorld.addListener(gameScreen);
        
        KeyboardInputHandler inputHandler = (KeyboardInputHandler) gameWorld.getPlayerTank().getInputHandler();
        inputHandler.setGameWorld(gameWorld);
        Gdx.input.setInputProcessor(inputHandler);
        
        gameWorld.initializeAiControllers();
    }

    @Override
    public void render() {
        gl.glClearColor(0f, 0f, 0.2f, 1f);
        gl.glClear(GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();

        gameWorld.update(deltaTime);
        gameWorld.render(gameWorld.batch);
        gameScreen.render(gameWorld.batch);
    }

    @Override
    public void dispose() {
        context.close();
    }
    
    // Other lifecycle methods (resize, pause, resume) are empty

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
