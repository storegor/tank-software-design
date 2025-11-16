package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.util.List;

public class Bullet implements GameObject, Disposable, OwnedProjectile {
    private final BulletModel model;
    private final BulletGraphics graphics;

    public Bullet(BulletModel model, BulletGraphics graphics) {
        this.model = model;
        this.graphics = graphics;
    }
    
    @Override
    public GameUnitModel getOwner() {
        return model.getOwner();
    }
    
    @Override
    public int getDamage() {
        return model.getDamage();
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        model.update(deltaTime, collidableObjects);
        graphics.update(model.getCoordinates(), model.getRotation(), model.getDestinationCoordinates(), model.getMovementProgress());
    }

    @Override
    public void render(Batch batch) {
        graphics.render(batch);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return model.getCoordinates();
    }

    public BulletModel getModel() {
        return model;
    }

    public BulletGraphics getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return graphics.getRectangle();
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }
}

