package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements GameWorldListener, Disposable {
    private final List<GameObject> renderableObjects;

    public GameScreen() {
        this.renderableObjects = new ArrayList<>();
    }

    @Override
    public void onGameObjectAdded(GameObject gameObject) {
        renderableObjects.add(gameObject);
    }

    @Override
    public void onGameObjectRemoved(GameObject gameObject) {
        renderableObjects.remove(gameObject);
    }

    public void render(Batch batch) {
        batch.begin();
        for (GameObject object : renderableObjects) {
            object.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (GameObject object : renderableObjects) {
            if (object instanceof Disposable) {
                ((Disposable) object).dispose();
            }
        }
    }
}

