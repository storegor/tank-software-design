package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class Obstacle extends AbstractGameObject {
    public Obstacle(Texture texture, GridPoint2 initialCoordinates) {
        super(texture, initialCoordinates);
    }

    @Override
    public void update(float deltaTime) {
        // Obstacles don't have update logic for now
    }

    @Override
    public void update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) {
        // Obstacles don't have update logic related to collidable objects for now
    }
} 
