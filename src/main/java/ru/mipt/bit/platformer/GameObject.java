package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public interface GameObject {
    void update(float deltaTime);
    void update(float deltaTime, List<? extends GameObject> collidableObjects);
    void render(Batch batch);
    GridPoint2 getCoordinates();
} 