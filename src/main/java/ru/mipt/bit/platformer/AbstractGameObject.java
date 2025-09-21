package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class AbstractGameObject implements GameObject {
    protected final Texture texture;
    protected final TextureRegion textureRegion;
    protected final Rectangle rectangle;
    protected final GridPoint2 coordinates;
    protected float rotation;

    public AbstractGameObject(Texture texture, GridPoint2 initialCoordinates) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.coordinates = initialCoordinates;
        this.rotation = 0f;
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, rotation);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void dispose() {
        texture.dispose();
    }
} 