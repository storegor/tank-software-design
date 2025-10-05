package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.List;

public interface GameObject {
    void update(float deltaTime);
    void update(float deltaTime, List<? extends AbstractGameObject> collidableObjects);
    void render(Batch batch);
} 