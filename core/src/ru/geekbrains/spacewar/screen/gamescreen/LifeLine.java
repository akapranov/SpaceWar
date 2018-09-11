package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.spacewar.base.Sprite;
import ru.geekbrains.spacewar.math.Rect;

public class LifeLine extends Sprite{
    private static final float LINE_HEIGHT = 0.02f;

    private float lineWidth;

    public LifeLine(TextureRegion region){
        super(region);
        setHeight(LINE_HEIGHT);
        this.lineWidth = 1f;
        setWidth(lineWidth);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }

    public void update(float delta, int lengthy) {
        super.update(delta);
        this.lineWidth = (float)(lengthy) * 0.01f;
    }
}
