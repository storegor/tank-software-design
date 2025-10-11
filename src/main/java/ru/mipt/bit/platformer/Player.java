package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.Collections;
import java.util.List;

public class Player implements GameObject, Disposable {

    private final GameUnitModel model;
    private final GameUnitGraphics graphics;
    private final TiledMapTileLayer groundLayer; 
    private final InputHandler inputHandler;

    public Player(GameUnitModel model, GameUnitGraphics graphics, InputHandler inputHandler) {
        this.model = model;
        this.graphics = graphics;
        this.inputHandler = inputHandler;
        this.groundLayer = null;
    }

    @Override
    public void update(float deltaTime) {
        update(deltaTime, Collections.emptyList());
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        Direction intendedDirection = inputHandler.getDirection();
        inputHandler.resetDirection(); 

        model.update(deltaTime, collidableObjects, intendedDirection);

        graphics.update(model.getCoordinates(), model.getRotation(), model.getDestinationCoordinates(), model.getMovementProgress());
    }

    @Override
    public void render(Batch batch) {
        graphics.render(batch);
    }

    public GridPoint2 getCoordinates() {
        return model.getCoordinates();
    }

    public Rectangle getRectangle() {
        return graphics.getRectangle();
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }
}
