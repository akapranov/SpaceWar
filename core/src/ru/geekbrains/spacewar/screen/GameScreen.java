package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.spacewar.base.Base2DScreen;
import ru.geekbrains.spacewar.math.Rect;
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
        hero = new Hero(atlas);
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
        batch.end();
    }

    public void update(float deltaTime) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(deltaTime);
        }
        hero.update(deltaTime);
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {

    }

    @Override
    public void dispose() {
        super.dispose();
        bgTexture.dispose();
        atlas.dispose();
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


}
