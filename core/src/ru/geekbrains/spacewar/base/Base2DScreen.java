package ru.geekbrains.spacewar.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/*
* Базовый класс для всех экранов
*/
public class Base2DScreen implements Screen{

    protected Game game;
    public Base2DScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
