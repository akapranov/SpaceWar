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

    public Hero(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        v.set(0 , 0);
        setHeightProportion(0.01f);
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
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        pos.set(posX,posY);
    }
}

