package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.spacewar.base.Base2DScreen;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.gamescreen.Bullet;
import ru.geekbrains.spacewar.screen.gamescreen.Enemy;
import ru.geekbrains.spacewar.screen.gamescreen.Explosion;
import ru.geekbrains.spacewar.screen.pool.BulletPool;
import ru.geekbrains.spacewar.screen.pool.EnemyPool;
import ru.geekbrains.spacewar.screen.pool.ExplosionPool;
import ru.geekbrains.spacewar.screen.sprites.Background;
import ru.geekbrains.spacewar.screen.gamescreen.Hero;
import ru.geekbrains.spacewar.screen.sprites.Star;
import ru.geekbrains.spacewar.utils.EnemyEmitter;

/*
 *  Экран Игры
 */

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 56;

    private Background background;
    private Texture bgTexture;
    private Hero hero;
    private Star star[];
    private TextureAtlas atlas;
    private BulletPool bulletPool = new BulletPool();

    private Music music;
    private Sound heroPiu;
    private Sound enemyPiu;

    private ExplosionPool explosionPool;
    private Sound explosionSound;

    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        this.music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();

        bgTexture = new Texture("textures/background.jpg");
        background = new Background(new TextureRegion(bgTexture));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        heroPiu = Gdx.audio.newSound(Gdx.files.internal("sounds/piu.wav"));
        enemyPiu = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        hero = new Hero(atlas, bulletPool, heroPiu, explosionPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, hero, enemyPiu);
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        hero.draw(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        hero.update(delta);
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generateEnemies(delta);
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + hero.getHalfWidth();
            if (enemy.pos.dst2(hero.pos) < minDist * minDist) {
                enemy.destroy();
                hero.destroy();
                //state = State.GAME_OVER;
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != hero || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    //if (enemy.isDestroyed()) {
                     //   frags++;
                    //    break;
                    //}
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == hero) {
                continue;
            }
            if (hero.isBulletCollision(bullet)) {
                hero.damage(bullet.getDamage());
                bullet.destroy();
                //if (hero.isDestroyed()) {
                  //  state = State.GAME_OVER;
                //}
            }
        }
    }

    public void deleteAllDestroyed() {
       bulletPool.freeAllDestroyedActiveSprites();
       explosionPool.freeAllDestroyedActiveSprites();
       enemyPool.freeAllDestroyedActiveSprites();
    }

    @Override
    public void dispose() {
        super.dispose();
        bgTexture.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        music.dispose();
        enemyPiu.dispose();
        heroPiu.dispose();
        explosionSound.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        hero.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        hero.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        hero.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        hero.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        hero.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
