package ru.mipt.bit.platformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AiTankControllerTest {

    @Mock
    private GameUnitModel aiTankModel;
    @Mock
    private CommandProcessor commandProcessor;

    private AiTankController aiTankController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aiTankController = new AiTankController(aiTankModel, commandProcessor);
    }

    @Test
    void updateAddsMoveCommandWhenMovementProgressIsOne() {
        when(aiTankModel.getMovementProgress()).thenReturn(1f);

        aiTankController.update();

        ArgumentCaptor<Command> commandCaptor = ArgumentCaptor.forClass(Command.class);
        verify(commandProcessor, times(1)).addCommand(commandCaptor.capture());

        Command capturedCommand = commandCaptor.getValue();
        assertNotNull(capturedCommand);
    }

    @Test
    void updateDoesNotAddCommandWhenMovementProgressIsNotOne() {
        when(aiTankModel.getMovementProgress()).thenReturn(0.5f);

        aiTankController.update();

        verify(commandProcessor, never()).addCommand(any());
    }
}
