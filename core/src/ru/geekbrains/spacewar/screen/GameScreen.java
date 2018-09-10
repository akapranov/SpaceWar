package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.spacewar.base.ActionListener;
import ru.geekbrains.spacewar.base.Base2DScreen;
import ru.geekbrains.spacewar.base.Font;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.gamescreen.Bullet;
import ru.geekbrains.spacewar.screen.gamescreen.ButtonNewGame;
import ru.geekbrains.spacewar.screen.gamescreen.Enemy;
import ru.geekbrains.spacewar.screen.gamescreen.LifeLine;
import ru.geekbrains.spacewar.screen.gamescreen.MessageGameOver;
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

public class GameScreen extends Base2DScreen implements ActionListener {

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame)
            startNewGame();
    }

    private enum State {PLAYING, GAME_OVER}

    private static final int STAR_COUNT = 56;
    private static final float FONT_SIZE  = 0.02f;

    private State state;
    private MessageGameOver messageGameOver;
    int frags;
    private Background background;
    private Texture bgTexture;
    private Texture lifelineTexture;
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

    private ButtonNewGame buttonNewGame;

    private static final String FRAGS = "Enemys killed: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    private LifeLine lifeLine;
    float lengthy;
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
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas,  this);
        lifelineTexture = new Texture("lifeline.png");
        lengthy = hero.getHp()/100.f;
        lifeLine = new LifeLine(new TextureRegion(lifelineTexture), 1f);
        font = new Font("font/font.fnt", "font/font.png");
        font.setWorldSize(FONT_SIZE);
        startNewGame();
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
        if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        lifeLine.draw(batch);
        batch.end();
    }

    public void update(float delta) {
        if (hero.isDestroyed()) {
            state = State.GAME_OVER;
        }
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state){
            case PLAYING:
                hero.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemyEmitter.generateEnemies(delta, frags);
                lifeLine.update(delta);
            case GAME_OVER:
                break;
        }
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
                state = State.GAME_OVER;
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
                    if (enemy.isDestroyed()) {
                        frags++;
                        break;
                    }
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
                if (hero.isDestroyed()) {
                    state = State.GAME_OVER;
                }
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
        lifeLine.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING){
            hero.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING){
            hero.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING){
            hero.touchDown(touch, pointer);
        }else{
            buttonNewGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING){
            hero.touchUp(touch, pointer);
        }else {
            buttonNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }
    private void startNewGame() {
        state = State.PLAYING;

        frags = 0;

        hero.startNewGame();
        enemyEmitter.startNewGame();

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }

    public void printInfo(){
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch,sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + 0.02f, worldBounds.getTop() - 0.02f);
        font.draw(batch,sbHP.append(HP).append(hero.getHp()), worldBounds.pos.x, worldBounds.getTop() - 0.02f, Align.center);
        font.draw(batch,sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight()- 0.02f, worldBounds.getTop() - 0.02f, Align.right );
    }
}
