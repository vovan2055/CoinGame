package com.vovangames.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class MainMenu extends ScreenAdapter {
    CoinGame g;

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
    }
    public MainMenu(CoinGame coinGame) {
        g = coinGame;
    }
    @Override
    public void hide() {
        g.stage.clear();
    }
    @Override
    public void render(float f) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(16640);
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        g.stage.draw();
    }
}
