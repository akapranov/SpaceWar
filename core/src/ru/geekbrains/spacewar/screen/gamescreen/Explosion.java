package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;

    public Explosion(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void set (float height, Vector2 position){
        this.pos.set(position);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        animateInterval += delta;
        if (animateTimer >= animateInterval){
            animateTimer = 0f;
            if (++frame == regions.length){
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
