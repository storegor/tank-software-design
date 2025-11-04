package ru.mipt.bit.platformer;

import java.util.LinkedList;
import java.util.Queue;

public class CommandProcessor {
    private final Queue<Command> commands = new LinkedList<>();

    public void addCommand(Command command) {
        commands.offer(command);
    }

    public void processCommands() {
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            command.execute();
        }
    }
}
