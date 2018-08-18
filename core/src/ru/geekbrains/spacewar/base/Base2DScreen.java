package ru.geekbrains.spacewar.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.spacewar.math.MatrixUtils;
import ru.geekbrains.spacewar.math.Rect;

/*
* Базовый класс для всех экранов
*/
public class Base2DScreen implements Screen, InputProcessor{

    protected SpriteBatch batch;

    protected Game game;
    private Rect screenBounds; //границы области рисования
    private  Rect worldBounds; // границы проекции мировых координат
    private Rect glBounds; // Дефолтные границы

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private final Vector2 touch = new Vector2();

    public Base2DScreen(Game game){
        this.game = game;
        Gdx.input.setInputProcessor(this);
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0,0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
    }

    @Override
    public void show() {
        if (batch != null) {
            throw new RuntimeException("Повторная установка screen без dispose");
        }
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setHeight(1f*aspect);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
