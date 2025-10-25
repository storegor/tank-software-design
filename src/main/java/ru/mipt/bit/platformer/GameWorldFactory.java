package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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
        InputHandler inputHandler = new KeyboardInputHandler();

        return new GameWorld(
                batch,
                LEVEL_PATH,
                GREEN_TREE_TEXTURE_PATH,
                BLUE_TANK_TEXTURE_PATH,
                PLAYER_MOVEMENT_SPEED,
                collisionDetector,
                inputHandler,
                levelGenerator);
    }
}
