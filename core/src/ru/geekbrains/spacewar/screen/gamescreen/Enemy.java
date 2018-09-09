package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.pool.BulletPool;
import ru.geekbrains.spacewar.screen.pool.ExplosionPool;

public class Enemy extends Ship {

    private enum State{ DESCENT, FIGHT}
    private State state;
    private Vector2 descentV = new Vector2(0f, -0.15f);

    private Hero hero;
    private Vector2 v0 = new Vector2();

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPooll, Sound piu, Hero hero, Rect worldBounds) {
        super(bulletPool,explosionPooll, piu, worldBounds);

        this.hero = hero;
        this.v.set(v0);
        this.reloadInterval = 0.1f;
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
            destroy();
        }
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()) {
                    hero.damage(bulletDamage);
                    destroy();
                }
                break;
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
        v.set(descentV);
        reloadTimer = reloadInterval;
        state = State.DESCENT;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }
}
