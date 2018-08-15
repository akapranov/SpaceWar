package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Base2DScreen;

/*
*  Экран Меню
*/
public class MenuScreen extends Base2DScreen{
    SpriteBatch batch;
    Texture backgraund;
    Texture hero;

    Vector2 position;
    Vector2 velocity;

    Vector2 touch;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        hero = new Texture("ship.png");
        backgraund = new Texture("background.jpg");

        position = new Vector2(0, 0);
        velocity = new Vector2(0.1f, 0.7f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(backgraund,0,0, 1024,780);
        batch.draw(hero, position.x, position.y, 52.75f, 53f);
        batch.end();
        position.add(velocity);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        backgraund.dispose();
        hero.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        return false;
    }
}
