package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public interface GameUnitGraphics extends Disposable {
    void update(GridPoint2 currentCoordinates, float rotation, GridPoint2 destinationCoordinates, float movementProgress);
    void update(GridPoint2 currentCoordinates);
    void render(Batch batch);
    Rectangle getRectangle();
}


