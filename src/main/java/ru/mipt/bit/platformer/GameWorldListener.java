package ru.mipt.bit.platformer;

public interface GameWorldListener {
    void onGameObjectAdded(GameObject gameObject);
    void onGameObjectRemoved(GameObject gameObject);
}

