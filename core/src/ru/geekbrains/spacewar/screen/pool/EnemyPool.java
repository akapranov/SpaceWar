package ru.geekbrains.spacewar.screen.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.spacewar.base.SpritesPool;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.gamescreen.Enemy;
import ru.geekbrains.spacewar.screen.gamescreen.Hero;

public class EnemyPool extends SpritesPool<Enemy>{

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound piu;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Hero hero, Sound piu) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.hero = hero;
        this.piu = piu;
    }

    private Hero hero;

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, piu, hero, worldBounds);
    }
}
