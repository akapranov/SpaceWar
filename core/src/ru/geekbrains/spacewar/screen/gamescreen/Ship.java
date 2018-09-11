package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Sprite;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.pool.BulletPool;
import ru.geekbrains.spacewar.screen.pool.ExplosionPool;

public class Ship extends Sprite {


    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected Sound sound;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected ExplosionPool explosionPool;

    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    public int getHp() {
        return hp;
    }

    public Ship(TextureRegion region, ExplosionPool explosionPool, int rows, int cols, int frames, Sound sound) {
        super(region, rows, cols, frames);
        this.explosionPool = explosionPool;
        this.sound = sound;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        sound.play();
    }

    public void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0){
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
        hp = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }
}
