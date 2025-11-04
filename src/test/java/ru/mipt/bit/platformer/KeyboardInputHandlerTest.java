package ru.mipt.bit.platformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.badlogic.gdx.Input.Keys;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class KeyboardInputHandlerTest {

    @Mock
    private GameUnitModel mockPlayerModel;
    @Mock
    private CommandProcessor mockCommandProcessor;

    private KeyboardInputHandler inputHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inputHandler = new KeyboardInputHandler(mockPlayerModel, mockCommandProcessor);
    }

    @Test
    void keyDownAddsMoveCommandForUp() {
        inputHandler.keyDown(Keys.UP);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.UP, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForW() {
        inputHandler.keyDown(Keys.W);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.UP, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForLeft() {
        inputHandler.keyDown(Keys.LEFT);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.LEFT, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForA() {
        inputHandler.keyDown(Keys.A);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.LEFT, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForDown() {
        inputHandler.keyDown(Keys.DOWN);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.DOWN, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForS() {
        inputHandler.keyDown(Keys.S);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.DOWN, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForRight() {
        inputHandler.keyDown(Keys.RIGHT);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.RIGHT, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyDownAddsMoveCommandForD() {
        inputHandler.keyDown(Keys.D);
        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(mockCommandProcessor, times(1)).addCommand(commandCaptor.capture());
        assertTrue(commandCaptor.getValue() instanceof MoveCommand);
        MoveCommand moveCommand = (MoveCommand) commandCaptor.getValue();
        assertEquals(Direction.RIGHT, moveCommand.getDirection());
        assertEquals(mockPlayerModel, moveCommand.getTankModel());
    }

    @Test
    void keyUpDoesNothing() {
        inputHandler.keyUp(Keys.UP);
        verify(mockCommandProcessor, never()).addCommand(any());
    }

    @Test
    void keyTypedDoesNothing() {
        inputHandler.keyTyped('c');
        verify(mockCommandProcessor, never()).addCommand(any());
    }

    @Test
    void touchDownDoesNothing() {
        inputHandler.touchDown(0, 0, 0, 0);
        verify(mockCommandProcessor, never()).addCommand(any());
    }

    @Test
    void touchUpDoesNothing() {
        inputHandler.touchUp(0, 0, 0, 0);
        verify(mockCommandProcessor, never()).addCommand(any());
    }

    @Test
    void touchDraggedDoesNothing() {
        inputHandler.touchDragged(0, 0, 0);
        verify(mockCommandProcessor, never()).addCommand(any());
    }

    @Test
    void mouseMovedDoesNothing() {
        inputHandler.mouseMoved(0, 0);
        verify(mockCommandProcessor, never()).addCommand(any());
    }

    @Test
    void scrolledDoesNothing() {
        inputHandler.scrolled(1);
        verify(mockCommandProcessor, never()).addCommand(any());
    }
}
