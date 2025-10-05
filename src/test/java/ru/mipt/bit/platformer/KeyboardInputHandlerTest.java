package ru.mipt.bit.platformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.badlogic.gdx.Input.Keys;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class KeyboardInputHandlerTest {

    private KeyboardInputHandler inputHandler;

    @BeforeEach
    void setUp() {
        inputHandler = new KeyboardInputHandler();
    }

    @Test
    void testInitialDirectionIsNull() {
        assertNull(inputHandler.getDirection());
    }

    @Test
    void testKeyDownUpSetsDirectionUp() {
        inputHandler.keyDown(Keys.UP);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testKeyDownW_SetsDirectionUp() {
        inputHandler.keyDown(Keys.W);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testKeyDownLeftSetsDirectionLeft() {
        inputHandler.keyDown(Keys.LEFT);
        assertEquals(Direction.LEFT, inputHandler.getDirection());
    }

    @Test
    void testKeyDownASetsDirectionLeft() {
        inputHandler.keyDown(Keys.A);
        assertEquals(Direction.LEFT, inputHandler.getDirection());
    }

    @Test
    void testKeyDownDownSetsDirectionDown() {
        inputHandler.keyDown(Keys.DOWN);
        assertEquals(Direction.DOWN, inputHandler.getDirection());
    }

    @Test
    void testKeyDownSSetsDirectionDown() {
        inputHandler.keyDown(Keys.S);
        assertEquals(Direction.DOWN, inputHandler.getDirection());
    }

    @Test
    void testKeyDownRightSetsDirectionRight() {
        inputHandler.keyDown(Keys.RIGHT);
        assertEquals(Direction.RIGHT, inputHandler.getDirection());
    }

    @Test
    void testKeyDownDSetsDirectionRight() {
        inputHandler.keyDown(Keys.D);
        assertEquals(Direction.RIGHT, inputHandler.getDirection());
    }

    @Test
    void testKeyUpResetsDirectionIfMatching() {
        inputHandler.keyDown(Keys.UP);
        assertEquals(Direction.UP, inputHandler.getDirection());
        inputHandler.keyUp(Keys.UP);
        assertNull(inputHandler.getDirection());
    }

    @Test
    void testKeyUpDoesNotResetDirectionIfDifferent() {
        inputHandler.keyDown(Keys.UP);
        assertEquals(Direction.UP, inputHandler.getDirection());
        inputHandler.keyUp(Keys.LEFT);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testResetDirectionMethod() {
        inputHandler.keyDown(Keys.UP);
        assertEquals(Direction.UP, inputHandler.getDirection());
        inputHandler.resetDirection();
        assertNull(inputHandler.getDirection());
    }

    @Test
    void testKeyTypedDoesNothing() {
        inputHandler.keyDown(Keys.UP);
        inputHandler.keyTyped('c');
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testTouchDownDoesNothing() {
        inputHandler.keyDown(Keys.UP);
        inputHandler.touchDown(0, 0, 0, 0);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testTouchUpDoesNothing() {
        inputHandler.keyDown(Keys.UP);
        inputHandler.touchUp(0, 0, 0, 0);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testTouchDraggedDoesNothing() {
        inputHandler.keyDown(Keys.UP);
        inputHandler.touchDragged(0, 0, 0);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testMouseMovedDoesNothing() {
        inputHandler.keyDown(Keys.UP);
        inputHandler.mouseMoved(0, 0);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }

    @Test
    void testScrolledDoesNothing() {
        inputHandler.keyDown(Keys.UP);
        inputHandler.scrolled(1);
        assertEquals(Direction.UP, inputHandler.getDirection());
    }
}
