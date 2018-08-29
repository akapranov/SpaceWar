package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Base2DScreen;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.pool.BulletPool;
import ru.geekbrains.spacewar.screen.sprites.Background;
import ru.geekbrains.spacewar.screen.gamescreen.Hero;
import ru.geekbrains.spacewar.screen.sprites.Star;

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

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("textures/background.jpg");
        background = new Background(new TextureRegion(bgTexture));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        heroPiu = Gdx.audio.newSound(Gdx.files.internal("sounds/piu.wav"));
        hero = new Hero(atlas, bulletPool, heroPiu);
        this.music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
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
        batch.end();
    }

    public void update(float deltaTime) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(deltaTime);
        }
        hero.update(deltaTime);
        bulletPool.updateActiveSprites(deltaTime);
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {
       bulletPool.freeAllDestroyedActiveSprites();
    }

    @Override
    public void dispose() {
        super.dispose();
        bgTexture.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        heroPiu.dispose();
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
