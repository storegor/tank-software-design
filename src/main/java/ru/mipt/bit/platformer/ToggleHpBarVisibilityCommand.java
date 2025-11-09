package ru.mipt.bit.platformer;

import java.util.List;

public class ToggleHpBarVisibilityCommand implements Command {
    private final List<HealthBarGraphicsDecorator> decorators;

    public ToggleHpBarVisibilityCommand(List<HealthBarGraphicsDecorator> decorators) {
        this.decorators = decorators;
    }

    @Override
    public void execute() {
        for (HealthBarGraphicsDecorator decorator : decorators) {
            decorator.toggleVisibility();
        }
    }
}
