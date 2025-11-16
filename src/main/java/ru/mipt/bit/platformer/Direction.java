package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1), 90f),
    DOWN(new GridPoint2(0, -1), -90f),
    LEFT(new GridPoint2(-1, 0), -180f),
    RIGHT(new GridPoint2(1, 0), 0f);

    private final GridPoint2 vector;
    private final float rotation;

    Direction(GridPoint2 vector, float rotation) {
        this.vector = vector;
        this.rotation = rotation;
    }

    public GridPoint2 getVector() {
        return vector;
    }

    public float getRotation() {
        return rotation;
    }

    public static Direction fromRotation(float rotation) {
        for (Direction dir : Direction.values()) {
            if (Math.abs(dir.rotation - rotation) < 0.01f) {
                return dir;
            }
        }
        return RIGHT;
    }
}
