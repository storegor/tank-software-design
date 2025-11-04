package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import java.util.List;

public class Tank implements GameObject, Disposable {
    private final GameUnitModel model;
    private final GameUnitGraphics graphics;
    private final CommandProcessor commandProcessor;
    private AiTankController aiTankController;

    public Tank(GameUnitModel model, GameUnitGraphics graphics) {
        this(model, graphics, null);
    }

    public Tank(GameUnitModel model, GameUnitGraphics graphics, AiTankController aiTankController) {
        this.model = model;
        this.graphics = graphics;
        this.commandProcessor = new CommandProcessor();
        this.aiTankController = aiTankController;
        if (this.aiTankController != null) {
            this.aiTankController.setCommandProcessor(this.commandProcessor);
        }
    }

    public void setAiTankController(AiTankController aiTankController) {
        this.aiTankController = aiTankController;
        if (this.aiTankController != null) {
            this.aiTankController.setCommandProcessor(this.commandProcessor);
        }
    }

    @Override
    public void update(float deltaTime) {
        update(deltaTime, null);
    }

    @Override
    public void update(float deltaTime, List<? extends GameObject> collidableObjects) {
        if (aiTankController != null) {
            aiTankController.update();
        }
        commandProcessor.processCommands();
        model.update(deltaTime, collidableObjects);
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

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public GameUnitModel getModel() {
        return model;
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }
}
