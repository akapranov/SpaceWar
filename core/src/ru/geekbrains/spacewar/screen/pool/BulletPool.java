package ru.geekbrains.spacewar.screen.pool;

import ru.geekbrains.spacewar.base.SpritesPool;
import ru.geekbrains.spacewar.screen.gamescreen.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected void debugLog() {
        System.out.println("active/free:" + activeObjects.size() + "/" + freeObjects.size());
    }
}
