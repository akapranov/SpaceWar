package ru.geekbrains.spacewar.screen.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Sprite;
import ru.geekbrains.spacewar.math.Rect;

public class Hero extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;
    //private Vector2 touch;

    public Hero(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"),1,2,2);
        v.set(0,0);
        //touch = new Vector2();
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
       float posX = 0;
       float posY = 0;
       pos.set(posX, posY);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }
}

