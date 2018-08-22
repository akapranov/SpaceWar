package ru.geekbrains.spacewar.screen;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.base.ActionListener;
import ru.geekbrains.spacewar.base.Base2DScreen;
import ru.geekbrains.spacewar.math.Rect;
import ru.geekbrains.spacewar.screen.menu.ButtonExit;
import ru.geekbrains.spacewar.screen.menu.ButtonNewGame;
import ru.geekbrains.spacewar.screen.sprites.Background;
import ru.geekbrains.spacewar.screen.sprites.Star;

/*
*  Экран Меню
*/
public class MenuScreen extends Base2DScreen implements ActionListener{

    private static final  int STAR_COUNT = 256;

    private static final float BUTTON_PRESS_SCALE =  0.9f;
    private static final float BUTTON_HEIGHT =  0.15f;


    private Background background;
    private Texture bgTexture;
    private Star star[];
    private TextureAtlas atlas;
    private ButtonExit buttonExit;
    private ButtonNewGame buttonNewGame;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("textures/background.jpg");
        background = new Background(new TextureRegion(bgTexture));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas, this, BUTTON_PRESS_SCALE );
        buttonExit.setHeightProportion(BUTTON_HEIGHT);
        buttonNewGame = new ButtonNewGame(atlas, this, BUTTON_PRESS_SCALE);
        buttonNewGame.setHeightProportion(BUTTON_HEIGHT);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void draw(){
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        buttonExit.draw(batch);
        buttonNewGame.draw(batch);
        batch.end();
    }

    public  void update(float deltaTime){
        for (int i = 0; i < star.length; i++) {
            star[i].update(deltaTime);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bgTexture.dispose();
        atlas.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonNewGame.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonNewGame.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonExit){
            Gdx.app.exit();
        }else if(src == buttonNewGame){
            game.setScreen(new GameScreen(game));
        }
    }
}
