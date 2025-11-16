package ru.mipt.bit.platformer;

public interface OwnedProjectile {
    GameUnitModel getOwner();
    int getDamage();
}

