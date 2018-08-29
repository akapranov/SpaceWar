package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.pool.BulletPool;
import ru.geekbrains.spacewar.screen.pool.ExplosionPool;

public class Enemy extends Ship {

    private Hero hero;
    private Vector2 v0 = new Vector2();

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPooll, Sound piu, Hero hero, Rect worldBounds) {
        super(bulletPool,explosionPooll, piu, worldBounds);
        this.hero = hero;
        this.v.set(v0);
        this.reloadInterval = 0.4f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
        if (getBottom() < worldBounds.getBottom()){
            boom();
            destroy();
        }
    }

    public void set(
        TextureRegion[] regions,
        Vector2 v0,
        TextureRegion bulletRegion,
        float bulletHeight,
        float bulletVY,
        int bulletDamage,
        float reloadInterval,
        float height,
        int hp){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        v.set(v0);
    }
}
