package ru.geekbrains.spacewar.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.spacewar.base.ActionListener;
import ru.geekbrains.spacewar.base.ScaledTouchUpButton;
import ru.geekbrains.spacewar.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {
    public ButtonExit(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btExit"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
    }
}
