package ru.geekbrains.spacewar.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.math.Rect;

public class Sprite extends Rect{
    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region){
        if (region == null) {
            throw new RuntimeException("region = null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(SpriteBatch batch){
        batch.draw(regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void resize(Rect worldBounds){

    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public void update(float delta){

    }
}
