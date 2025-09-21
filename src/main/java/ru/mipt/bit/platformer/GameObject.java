package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface GameObject {
    void update(float deltaTime);
    void render(Batch batch);
} 