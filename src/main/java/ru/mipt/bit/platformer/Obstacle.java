package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class Obstacle extends AbstractGameObject {
    public Obstacle(Texture texture, GridPoint2 initialCoordinates) {
        super(texture, initialCoordinates);
    }

    @Override
    public void update(float deltaTime) {
        // Obstacles don't have update logic for now
    }
} 
