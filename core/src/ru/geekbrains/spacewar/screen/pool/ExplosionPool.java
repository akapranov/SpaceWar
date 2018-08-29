package ru.geekbrains.spacewar.screen.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.spacewar.base.SpritesPool;
import ru.geekbrains.spacewar.screen.gamescreen.Explosion;

public class ExplosionPool extends SpritesPool<Explosion>{

    private TextureRegion region;

    public ExplosionPool(TextureAtlas atlas){
        region = atlas.findRegion("explosion");
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(region, 9,9, 74);
    }
}
