package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.spacewar.base.Sprite;
import ru.geekbrains.spacewar.math.Rect;

public class LifeLine extends Sprite{
    private static final float LINE_HEIGHT = 0.02f;


    public LifeLine(TextureRegion region){
        super(region);
        setSize(1f, LINE_HEIGHT);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }

    public void update(float delta, int lengthy) {
        super.update(delta);
        setWidth((float)(lengthy) * 0.01f);
    }
}
