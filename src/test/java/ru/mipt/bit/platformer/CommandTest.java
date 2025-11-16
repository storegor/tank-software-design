package ru.mipt.bit.platformer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CommandTest {

    @Test
    void moveCommandSetsDirectionOnModel() {
        GameUnitModel mockModel = Mockito.mock(GameUnitModel.class);
        Direction direction = Direction.UP;
        MoveCommand command = new MoveCommand(mockModel, direction);
        command.execute();
        verify(mockModel, times(1)).setDirection(direction);
    }

    @Test
    void doNothingCommandDoesNothing() {
        DoNothingCommand command = new DoNothingCommand();
        assertDoesNotThrow(command::execute);
    }

    @Test
    void commandProcessorProcessesAllCommands() {
        CommandProcessor processor = new CommandProcessor();
        Command mockCommand1 = Mockito.mock(Command.class);
        Command mockCommand2 = Mockito.mock(Command.class);

        processor.addCommand(mockCommand1);
        processor.addCommand(mockCommand2);
        processor.processCommands();

        verify(mockCommand1, times(1)).execute();
        verify(mockCommand2, times(1)).execute();
    }
}
