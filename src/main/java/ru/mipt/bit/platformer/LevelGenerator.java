package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface LevelGenerator {
    LevelData generateLevel(TiledMapTileLayer groundLayer);
}
