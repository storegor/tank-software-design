package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameWorldFactory {

    private static final String LEVEL_PATH = "level.tmx";
    private static final String GREEN_TREE_TEXTURE_PATH = "images/greenTree.png";
    private static final String BLUE_TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final float PLAYER_MOVEMENT_SPEED = 0.4f;

    public GameWorld createGameWorld(Batch batch, LevelGenerator levelGenerator) {
        TiledMap level = new TmxMapLoader().load(LEVEL_PATH);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        CollisionDetector collisionDetector = new TileCollisionDetector(groundLayer);

        LevelData levelData = levelGenerator.generateLevel(groundLayer);

        List<HealthBarGraphicsDecorator> healthBarDecorators = new ArrayList<>();

        Texture blueTankTexture = new Texture(BLUE_TANK_TEXTURE_PATH);
        PlayerModel playerModel = new PlayerModel(levelData.getPlayerStart(), collisionDetector, PLAYER_MOVEMENT_SPEED);
        PlayerGraphics playerGraphics = new PlayerGraphics(blueTankTexture, playerModel.getCoordinates(), playerModel.getRotation(), groundLayer, Interpolation.smooth);
        HealthBarGraphicsDecorator playerHealthBar = new HealthBarGraphicsDecorator(playerGraphics, playerModel);
        healthBarDecorators.add(playerHealthBar);
        PlayerTank playerTank = new PlayerTank(playerModel, playerHealthBar, healthBarDecorators);

        List<Tank> aiTanks = new ArrayList<>();
        for (GridPoint2 aiTankPos : levelData.getAiTankPositions()) {
            GameUnitModel aiTankModel = new PlayerModel(aiTankPos, collisionDetector, PLAYER_MOVEMENT_SPEED);
            GameUnitGraphics aiTankGraphics = new PlayerGraphics(blueTankTexture, aiTankModel.getCoordinates(), aiTankModel.getRotation(), groundLayer, Interpolation.smooth);
            HealthBarGraphicsDecorator aiHealthBar = new HealthBarGraphicsDecorator(aiTankGraphics, aiTankModel);
            healthBarDecorators.add(aiHealthBar);
            Tank aiTank = new Tank(aiTankModel, aiHealthBar);
            AiTankController aiTankController = new AiTankController(aiTankModel, aiTank.getCommandProcessor());
            aiTank.setAiTankController(aiTankController);
            aiTanks.add(aiTank);
        }

        return new GameWorld(
                batch,
                LEVEL_PATH,
                GREEN_TREE_TEXTURE_PATH,
                PLAYER_MOVEMENT_SPEED,
                collisionDetector,
                levelData,
                playerTank,
                aiTanks,
                healthBarDecorators);
    }
}
