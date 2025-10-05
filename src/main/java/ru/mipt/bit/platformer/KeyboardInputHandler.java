package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input.Keys;

public class KeyboardInputHandler implements InputHandler {

    private Direction currentDirection = null;

    @Override
    public Direction getDirection() {
        return currentDirection;
    }

    @Override
    public void resetDirection() {
        this.currentDirection = null;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.UP || keycode == Keys.W) {
            currentDirection = Direction.UP;
        } else if (keycode == Keys.LEFT || keycode == Keys.A) {
            currentDirection = Direction.LEFT;
        } else if (keycode == Keys.DOWN || keycode == Keys.S) {
            currentDirection = Direction.DOWN;
        } else if (keycode == Keys.RIGHT || keycode == Keys.D) {
            currentDirection = Direction.RIGHT;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Reset direction only if the released key was the one setting the current direction
        if ((keycode == Keys.UP || keycode == Keys.W) && currentDirection == Direction.UP) {
            currentDirection = null;
        } else if ((keycode == Keys.LEFT || keycode == Keys.A) && currentDirection == Direction.LEFT) {
            currentDirection = null;
        } else if ((keycode == Keys.DOWN || keycode == Keys.S) && currentDirection == Direction.DOWN) {
            currentDirection = null;
        } else if ((keycode == Keys.RIGHT || keycode == Keys.D) && currentDirection == Direction.RIGHT) {
            currentDirection = null;
        }
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

    // Removed @Override and scrolled(float amountX, float amountY)

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
