package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mipt.bit.platformer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

@Configuration
public class AppConfig {

    private static final String LEVEL_PATH = "level.tmx";
    private static final String BLUE_TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String GREEN_TREE_TEXTURE_PATH = "images/greenTree.png";
    private static final float PLAYER_MOVEMENT_SPEED = 0.4f;

    @Bean
    public Batch batch() {
        return new SpriteBatch();
    }

    @Bean(destroyMethod = "dispose")
    public TiledMap tiledMap() {
        return new TmxMapLoader().load(LEVEL_PATH);
    }

    @Bean
    public TiledMapTileLayer groundLayer(TiledMap tiledMap) {
        return getSingleLayer(tiledMap);
    }

    @Bean
    public CollisionDetector collisionDetector(TiledMapTileLayer groundLayer) {
        return new TileCollisionDetector(groundLayer);
    }

    @Bean
    public LevelGenerator levelGenerator() {
        return new RandomLevelGenerator();
    }

    @Bean(destroyMethod = "dispose")
    public Texture blueTankTexture() {
        return new Texture(BLUE_TANK_TEXTURE_PATH);
    }

    @Bean(destroyMethod = "dispose")
    public Texture greenTreeTexture() {
        return new Texture(GREEN_TREE_TEXTURE_PATH);
    }

    @Bean
    public GameWorld gameWorld(Batch batch, TiledMap tiledMap, CollisionDetector collisionDetector, LevelGenerator levelGenerator, Texture blueTankTexture, Texture greenTreeTexture) {
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        LevelData levelData = levelGenerator.generateLevel(groundLayer);

        PlayerModel playerModel = new PlayerModel(levelData.getPlayerStart(), collisionDetector, PLAYER_MOVEMENT_SPEED);
        PlayerGraphics playerGraphics = new PlayerGraphics(blueTankTexture, playerModel.getCoordinates(), playerModel.getRotation(), groundLayer, Interpolation.smooth);
        HealthBarGraphicsDecorator playerHealthBar = new HealthBarGraphicsDecorator(playerGraphics, playerModel);
        PlayerTank playerTank = new PlayerTank(playerModel, playerHealthBar, new ArrayList<>());

        List<Tank> aiTanks = new ArrayList<>();
        for (GridPoint2 aiTankPos : levelData.getAiTankPositions()) {
            GameUnitModel aiTankModel = new PlayerModel(aiTankPos, collisionDetector, PLAYER_MOVEMENT_SPEED);
            GameUnitGraphics aiTankGraphics = new PlayerGraphics(blueTankTexture, aiTankModel.getCoordinates(), aiTankModel.getRotation(), groundLayer, Interpolation.smooth);
            HealthBarGraphicsDecorator aiHealthBar = new HealthBarGraphicsDecorator(aiTankGraphics, aiTankModel);
            Tank aiTank = new Tank(aiTankModel, aiHealthBar);
            aiTank.setAiTankController(new AiTankController(aiTankModel, aiTank.getCommandProcessor()));
            aiTanks.add(aiTank);
        }

        List<Obstacle> obstacles = levelData.getObstaclePositions().stream()
                .map(pos -> {
                    ObstacleModel model = new ObstacleModel(pos);
                    ObstacleGraphics graphics = new ObstacleGraphics(greenTreeTexture, model.getCoordinates(), groundLayer);
                    return new Obstacle(model, graphics);
                })
                .collect(Collectors.toList());

        List<HealthBarGraphicsDecorator> healthBarDecorators = Stream.concat(
                Stream.of(playerHealthBar),
                aiTanks.stream().map(tank -> (HealthBarGraphicsDecorator) tank.getGraphics())
        ).collect(Collectors.toList());
        ((KeyboardInputHandler) playerTank.getInputHandler()).setDecorators(healthBarDecorators);

        GameWorld gameWorld = new GameWorld(batch, tiledMap, collisionDetector, playerTank, aiTanks, obstacles, healthBarDecorators);
        
        gameWorld.addSharedResource(blueTankTexture);
        gameWorld.addSharedResource(greenTreeTexture);

        return gameWorld;
    }
}
