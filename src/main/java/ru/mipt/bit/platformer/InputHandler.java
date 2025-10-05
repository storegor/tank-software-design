package ru.mipt.bit.platformer;

import com.badlogic.gdx.InputProcessor;

public interface InputHandler extends InputProcessor {
    Direction getDirection();
    void resetDirection();
}
