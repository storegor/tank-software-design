package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class ShootCommand implements Command {
    private final GameUnitModel tankModel;
    private final GameWorld gameWorld;

    public ShootCommand(GameUnitModel tankModel, GameWorld gameWorld) {
        this.tankModel = tankModel;
        this.gameWorld = gameWorld;
    }

    @Override
    public void execute() {
        Direction direction = tankModel.getCurrentDirection();
        if (direction == null) {
            float rotation = tankModel.getRotation();
            direction = Direction.fromRotation(rotation);
        }

        GridPoint2 tankPos = tankModel.getDestinationCoordinates().cpy();

        BulletModel bulletModel = new BulletModel(tankPos, direction, gameWorld.getCollisionDetector(), tankModel);
        BulletGraphics bulletGraphics = new BulletGraphics(tankPos, gameWorld.getGroundLayer());
        Bullet bullet = new Bullet(bulletModel, bulletGraphics);

        gameWorld.addBullet(bullet);
    }

    public GameUnitModel getTankModel() {
        return tankModel;
    }
}

