package ru.mipt.bit.platformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TankTest {

    @Mock
    private GameUnitModel mockModel;
    @Mock
    private GameUnitGraphics mockGraphics;
    @Mock
    private AiTankController mockAiTankController;
    @Mock
    private InputHandler mockInputHandler;

    private Tank tank;
    private PlayerTank playerTank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tank = new Tank(mockModel, mockGraphics, mockAiTankController);
        playerTank = new PlayerTank(mockModel, mockGraphics);
    }

    @Test
    void updateCallsAiTankControllerUpdateIfPresent() {
        tank.update(1f, Collections.emptyList());
        verify(mockAiTankController, times(1)).update();
    }

    @Test
    void updateDoesNotCallAiTankControllerUpdateIfNotPresent() {
        Tank simpleTank = new Tank(mockModel, mockGraphics);
        simpleTank.update(1f, Collections.emptyList());
        verify(mockAiTankController, never()).update();
    }

    @Test
    void updateCallsCommandProcessorProcessCommands() {
        tank.update(1f, Collections.emptyList());
        verify(tank.getCommandProcessor(), times(1)).processCommands();
    }

    @Test
    void updateCallsModelUpdate() {
        tank.update(1f, Collections.emptyList());
        verify(mockModel, times(1)).update(eq(1f), any());
    }

    @Test
    void updateCallsGraphicsUpdate() {
        tank.update(1f, Collections.emptyList());
        verify(mockGraphics, times(1)).update(any(), any(), any(), any());
    }
}
