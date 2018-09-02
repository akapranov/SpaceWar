package ru.geekbrains.spacewar.screen.gamescreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.spacewar.base.ActionListener;
import ru.geekbrains.spacewar.base.ScaledTouchUpButton;

public class ButtonNewGame extends ScaledTouchUpButton {
    private static final float HEIGHT = 0.07f;
    private static final float BOTTOM_MARGIN = -0.17f;
    private static final float PRESS_SCALE = 0.9f;

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener, PRESS_SCALE);
        setHeightProportion(HEIGHT);
        setBottom(BOTTOM_MARGIN);
    }
}
