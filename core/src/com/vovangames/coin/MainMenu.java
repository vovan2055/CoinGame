package com.vovangames.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class MainMenu extends ScreenAdapter {

    /* renamed from: g */
    MyGdxGame f271g;

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void show() {
    }

    public MainMenu(MyGdxGame myGdxGame) {
        this.f271g = myGdxGame;
    }

    @Override
    public void hide() {
        this.f271g.stage.clear();
    }

    @Override
    public void render(float f) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(16640);
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, (float) 1);
        this.f271g.stage.draw();
    }
}
