package com.vovangames.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.vovangames.coin.Platform;
import com.vovangames.coin.utils.Cube;

public class TutorialRoom extends ScreenAdapter {
    public int actorSize = 50;
    public Cube coin;
    MyGdxGame g;
    public Cube player;
    public Cube sharp;
    public Stage stage;
    Touchpad touchpad;
    Touchpad.TouchpadStyle ts;

    public TutorialRoom(MyGdxGame myGdxGame) {
        g = myGdxGame;
    }

    @Override
    public void show() {
        player = new Cube(new Texture(Gdx.files.internal("player.png")));
        sharp = new Cube(new Texture(Gdx.files.internal("sharp.png")));
        coin = new Cube(new Texture(Gdx.files.internal("cparticle.png")));
        player.setPosition((float) (Gdx.graphics.getWidth() / 2), (float) (Gdx.graphics.getHeight() / 2));
        player.setSize((float) this.actorSize, (float) this.actorSize);
        sharp.setSize((float) this.actorSize, (float) this.actorSize);
        coin.setPosition((float) (Gdx.graphics.getWidth() - this.actorSize), (float) (Gdx.graphics.getHeight() / 2));
        coin.setSize((float) this.actorSize, (float) this.actorSize);
        coin.system.fixedAngle = true;
        coin.system.lifetime = 2.0f;
        coin.system.angle = (float) 45;

        ts = new Touchpad.TouchpadStyle();



        ts.background = ts.knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("touchpad.png"))));

        touchpad = new Touchpad(0, ts);
        touchpad.setSize(300, 300);
        touchpad.setColor(1, 1, 1, 0.6f);
        stage = new Stage();
        stage.addActor(this.player);
        stage.addActor(this.sharp);
        stage.addActor(this.coin);
        stage.addActor(this.touchpad);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (this.touchpad.isTouched()) {
            this.player.moveBy(this.touchpad.getKnobPercentX() * ((float) 10), this.touchpad.getKnobPercentY() * ((float) 10));
        }
        if (this.player.collidesWith(this.sharp)) {
            Gdx.app.exit();
        }
        if (this.player.collidesWith(this.coin)) {

            g.setScreen(new GameRoom(g, Platform.ANDROID));
        }
        this.stage.draw();
    }

    @Override
    public void resize(int i, int i2) {
        this.stage.getCamera().viewportWidth = (float) i;
        this.stage.getCamera().viewportHeight = (float) i2;
    }
}
