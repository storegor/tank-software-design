package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {
    private final String levelFilePath;

    public FileLevelGenerator(String levelFilePath) {
        this.levelFilePath = levelFilePath;
    }

    @Override
    public LevelData generateLevel(TiledMapTileLayer groundLayer) {
        GridPoint2 playerStart = null;
        List<GridPoint2> obstaclePositions = new ArrayList<>();
        List<GridPoint2> aiTankPositions = new ArrayList<>();
        List<String> lines = new ArrayList<>();



try (InputStream is = getClass().getClassLoader().getResourceAsStream(levelFilePath);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                throw new LevelLoadingException("Failed to read level file", e, levelFilePath);
            }

            Collections.reverse(lines);

            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    switch (c) {
                        case 'T':
                            obstaclePositions.add(new GridPoint2(x, y));
                            break;
                        case 'X':
                            if (playerStart != null) {
                                throw new LevelLoadingException("Multiple player start positions found", levelFilePath, y, x);
                            }
                            playerStart = new GridPoint2(x, y);
                            break;
                        case 'A':
                            aiTankPositions.add(new GridPoint2(x, y));
                            break;
                        case '_':
                            break;
                        default:
                            throw new LevelLoadingException("Unknown character in level file: " + c, levelFilePath, y, x);
                    }
                }
            }

            if (playerStart == null) {
                throw new LevelLoadingException("Player start position not found", levelFilePath);
            }

            return new LevelData(playerStart, obstaclePositions, aiTankPositions);
        }
    }
