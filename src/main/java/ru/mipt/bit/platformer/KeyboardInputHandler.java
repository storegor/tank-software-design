package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input.Keys;

import java.util.List;

public class KeyboardInputHandler implements InputHandler {

    private final CommandProcessor commandProcessor;
    private final GameUnitModel playerModel;
    private final List<HealthBarGraphicsDecorator> decorators;
    private GameWorld gameWorld;

    public KeyboardInputHandler(GameUnitModel playerModel, CommandProcessor commandProcessor, List<HealthBarGraphicsDecorator> decorators) {
        this.playerModel = playerModel;
        this.commandProcessor = commandProcessor;
        this.decorators = decorators;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void handleInput(CommandProcessor commandProcessor) {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (playerModel.getCurrentDirection() == null) {
            if (keycode == Keys.UP || keycode == Keys.W) {
                commandProcessor.addCommand(new MoveCommand(playerModel, Direction.UP));
            } else if (keycode == Keys.LEFT || keycode == Keys.A) {
                commandProcessor.addCommand(new MoveCommand(playerModel, Direction.LEFT));
            } else if (keycode == Keys.DOWN || keycode == Keys.S) {
                commandProcessor.addCommand(new MoveCommand(playerModel, Direction.DOWN));
            } else if (keycode == Keys.RIGHT || keycode == Keys.D) {
                commandProcessor.addCommand(new MoveCommand(playerModel, Direction.RIGHT));
            }
        }
        if (keycode == Keys.L) {
            commandProcessor.addCommand(new ToggleHpBarVisibilityCommand(decorators));
        }
        if (keycode == Keys.SPACE && gameWorld != null) {
            commandProcessor.addCommand(new ShootCommand(playerModel, gameWorld));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
