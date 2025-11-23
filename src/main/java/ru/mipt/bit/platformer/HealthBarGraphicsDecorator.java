package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class HealthBarGraphicsDecorator implements GameUnitGraphics {
    private final GameUnitGraphics wrappee;
    private final GameUnitModel model;
    private boolean visible = false;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private static final float HEALTH_BAR_WIDTH = 80f;
    private static final float HEALTH_BAR_HEIGHT = 8f;
    private static final float HEALTH_BAR_Y_OFFSET = 90f;

    public HealthBarGraphicsDecorator(GameUnitGraphics wrappee, GameUnitModel model) {
        this.wrappee = wrappee;
        this.model = model;
    }

    @Override
    public void render(Batch batch) {
        wrappee.render(batch);
        if (visible) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
            shape_renderer_begin();
            drawHealthBar();
            shapeRenderer.end();
            batch.begin();
        }
    }

    private void shape_renderer_begin() {
        if (!shapeRenderer.isDrawing()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }
    }

    private void drawHealthBar() {
        Rectangle interpolatedRectangle = wrappee.getRectangle();
        float x = interpolatedRectangle.x + (interpolatedRectangle.width - HEALTH_BAR_WIDTH) / 2;
        float y = interpolatedRectangle.y + HEALTH_BAR_Y_OFFSET;
        float hpPercentage = (float) model.getHp() / model.getMaxHp();

        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(x, y, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        shapeRenderer.setColor(1 - hpPercentage, hpPercentage, 0, 1);
        shapeRenderer.rect(x, y, HEALTH_BAR_WIDTH * hpPercentage, HEALTH_BAR_HEIGHT);
    }


    public void toggleVisibility() {
        visible = !visible;
    }

    @Override
    public void update(GridPoint2 currentCoordinates, float rotation, GridPoint2 destinationCoordinates, float movementProgress) {
        wrappee.update(currentCoordinates, rotation, destinationCoordinates, movementProgress);
    }

    @Override
    public void update(GridPoint2 currentCoordinates) {
        wrappee.update(currentCoordinates);
    }

    @Override
    public Rectangle getRectangle() {
        return wrappee.getRectangle();
    }

    @Override
    public void dispose() {
        wrappee.dispose();
        try {
            shapeRenderer.dispose();
        } catch (IllegalArgumentException e) {
        }
    }
}
