package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstacleModelTest {

    @Test
    void testInitialCoordinates() {
        GridPoint2 initialCoordinates = new GridPoint2(5, 5);
        ObstacleModel obstacleModel = new ObstacleModel(initialCoordinates);
        assertEquals(initialCoordinates, obstacleModel.getCoordinates());
    }

    @Test
    void testCoordinatesAreCopies() {
        GridPoint2 initialCoordinates = new GridPoint2(5, 5);
        ObstacleModel obstacleModel = new ObstacleModel(initialCoordinates);
        initialCoordinates.x = 10;
        initialCoordinates.y = 10;
        assertEquals(new GridPoint2(5, 5), obstacleModel.getCoordinates());
    }
}
