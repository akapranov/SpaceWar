package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Base2DScreen;
import ru.geekbrains.spacewar.base.Sprite;
import ru.geekbrains.spacewar.math.Rect;

/*
*  Экран Меню
*/
public class MenuScreen extends Base2DScreen{
    public static final float SPEED = 1.f;
    Texture backgraund;
    Sprite bg;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        backgraund = new Texture("background.jpg");
        bg = new Sprite(new TextureRegion(backgraund));
        bg.setSize(1f, 1f);
        }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        bg.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgraund.dispose();

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
