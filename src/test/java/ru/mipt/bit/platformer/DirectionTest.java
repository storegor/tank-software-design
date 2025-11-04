package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    void testUpDirection() {
        assertEquals(new GridPoint2(0, 1), Direction.UP.getVector());
        assertEquals(90f, Direction.UP.getRotation());
    }

    @Test
    void testDownDirection() {
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getVector());
        assertEquals(-90f, Direction.DOWN.getRotation());
    }

    @Test
    void testLeftDirection() {
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getVector());
        assertEquals(-180f, Direction.LEFT.getRotation());
    }

    @Test
    void testRightDirection() {
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getVector());
        assertEquals(0f, Direction.RIGHT.getRotation());
    }
}
