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
        // Препятствия пока не имеют логики обновления
    }

    @Override
    public void update(float deltaTime, List<? extends AbstractGameObject> collidableObjects) {
        // Препятствия пока не имеют логики обновления, связанной с другими объектами для столкновений
    }
} 
