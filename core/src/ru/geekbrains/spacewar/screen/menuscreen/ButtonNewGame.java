package ru.geekbrains.spacewar.screen.menuscreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.spacewar.base.ActionListener;
import ru.geekbrains.spacewar.base.ScaledTouchUpButton;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btPlay"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.02f);
        setLeft(worldBounds.getLeft() + 0.02f);
    }
}
