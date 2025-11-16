package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileLevelGeneratorTest {

    private static final String TEST_LEVEL_FILE = "src/main/resources/test_level.txt";

    @Mock
    private TiledMapTileLayer groundLayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void createTestFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(TEST_LEVEL_FILE)) {
            writer.write(content);
        }
    }

    @Test
    void generateLevelParsesCorrectFile() throws IOException {
        String levelContent = "___T__T___\n__TT__TTTA\n_________T\nTTTT__T__A\n_____X____\n___A______";
        createTestFile(levelContent);

        FileLevelGenerator generator = new FileLevelGenerator("test_level.txt");
        LevelData levelData = generator.generateLevel(groundLayer);

        assertEquals(new GridPoint2(5, 1), levelData.getPlayerStart());
        assertEquals(Arrays.asList(new GridPoint2(3, 5), new GridPoint2(6, 5), new GridPoint2(2, 4), new GridPoint2(3, 4), new GridPoint2(6, 4), new GridPoint2(7, 4), new GridPoint2(9, 3), new GridPoint2(0, 2), new GridPoint2(1, 2), new GridPoint2(2, 2), new GridPoint2(3, 2), new GridPoint2(6, 2), new GridPoint2(8, 2)), levelData.getObstaclePositions());
        assertEquals(Arrays.asList(new GridPoint2(8, 4), new GridPoint2(9, 2), new GridPoint2(3, 0)), levelData.getAiTankPositions());
    }

    @Test
    void generateLevelThrowsExceptionForMultiplePlayers() throws IOException {
        String levelContent = "X__\nX__";
        createTestFile(levelContent);

        FileLevelGenerator generator = new FileLevelGenerator("test_level.txt");
        LevelLoadingException exception = assertThrows(LevelLoadingException.class, () -> generator.generateLevel(groundLayer));
        assertTrue(exception.getMessage().contains("Multiple player start positions found"));
        assertEquals("test_level.txt", exception.getFilePath());
        assertEquals(0, exception.getLine());
        assertEquals(0, exception.getColumn());
    }

    @Test
    void generateLevelThrowsExceptionForNoPlayer() throws IOException {
        String levelContent = "___";
        createTestFile(levelContent);

        FileLevelGenerator generator = new FileLevelGenerator("test_level.txt");
        LevelLoadingException exception = assertThrows(LevelLoadingException.class, () -> generator.generateLevel(groundLayer));
        assertTrue(exception.getMessage().contains("Player start position not found"));
        assertEquals("test_level.txt", exception.getFilePath());
    }

    @Test
    void generateLevelThrowsExceptionForUnknownCharacter() throws IOException {
        String levelContent = "_?_";
        createTestFile(levelContent);

        FileLevelGenerator generator = new FileLevelGenerator("test_level.txt");
        LevelLoadingException exception = assertThrows(LevelLoadingException.class, () -> generator.generateLevel(groundLayer));
        assertTrue(exception.getMessage().contains("Unknown character"));
        assertEquals("test_level.txt", exception.getFilePath());
        assertEquals(0, exception.getLine());
        assertEquals(1, exception.getColumn());
    }

    @AfterAll
    static void tearDown() {
        File testFile = new File(TEST_LEVEL_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
