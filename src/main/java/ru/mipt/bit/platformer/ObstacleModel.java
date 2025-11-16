package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class ObstacleModel {
    private final GridPoint2 coordinates;

    public ObstacleModel(GridPoint2 initialCoordinates) {
        this.coordinates = new GridPoint2(initialCoordinates);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
