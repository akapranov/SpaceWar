package ru.geekbrains.spacewar.screen.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Sprite;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.math.Rnd;

public class Hero extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    private Vector2 pos;
    private Vector2 touch;

    public Hero(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        v = new Vector2(0,0);
        pos = new Vector2(0,0);
        touch = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
   }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = worldBounds.getHalfWidth();
        float posY = worldBounds.getBottom();
        pos.set(posX,posY);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }
}

