package com.vovangames.coin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.vovangames.coin.net.Net;

public class CoinGame extends Game {
    TextField IPinput;
    OrthographicCamera cam;
    Dialog d;
    CoinGame g = this;
    Label host;
    Label join;
    public Platform p;
    Stage stage;
    Label start;

    @Override
    public void create() {


        setScreen(new MainMenu(g));

        stage = new Stage();
        stage.setDebugAll(true);
        start = new Label("CTAPT", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        start.setAlignment(Align.center);
        start.setOrigin(start.getWidth() / 2, start.getHeight() / 2);
        start.setFontScale(5);
        start.setX((float) ((Gdx.graphics.getWidth() / 2) - 150));
        start.setY((float) ((Gdx.graphics.getHeight() / 2) + 100));
        start.setSize(300, 100);


        host = new Label("XOCT", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        host.setFontScale((float) 5);
        host.setSize((float) HttpStatus.SC_MULTIPLE_CHOICES, (float) 100);
        host.setAlignment(1);
        host.setPosition(start.getX(), start.getY() - 150);


        join = new Label("OK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        join.setFontScale((float) 5);
        join.setSize((float) HttpStatus.SC_MULTIPLE_CHOICES, (float) 100);
        join.setAlignment(1);
        join.setPosition(start.getX(), host.getY() - 150);

        Window.WindowStyle windowStyle2 = new Window.WindowStyle();
        windowStyle2.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("dialog.png"))));
        windowStyle2.titleFont = new BitmapFont();
        windowStyle2.titleFontColor = Color.CYAN;
        d = new Dialog("enter IP", windowStyle2);
        d.setSize(((float) Gdx.graphics.getWidth()) * 0.7f, ((float) Gdx.graphics.getHeight()) / 2.5f);
        d.setZIndex(2);

        TextField.TextFieldStyle ts = new TextField.TextFieldStyle();

        ts.font = new BitmapFont();
        ts.fontColor = Color.WHITE;
        ts.messageFont = ts.font;
        ts.messageFontColor = Color.GRAY;

        IPinput = new TextField("", ts);
        IPinput.setAlignment(Align.center);
        IPinput.setMessageText("                    IP...");
        IPinput.setSize(300, 40);

        IPinput.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent inputEvent, int i) {
                if (i == Input.Keys.ENTER) {

                    GameRoom room = new GameRoom(g, p);
                    room.serverMode = 2;
                    Net.address = IPinput.getText();
                    Net.handler = room;
                    setScreen(room);
                }
                return false;
            }
        });
        d.addActor(IPinput);
        IPinput.setFillParent(true);
        IPinput.setOrigin(IPinput.getWidth() / ((float) 2), IPinput.getHeight() / 2);
        stage.addActor(host);
        stage.addActor(join);
        stage.addActor(start);
        Gdx.input.setInputProcessor(stage);

        start.addListener(new ClickListener() {
            public void clicked(InputEvent inputEvent, float f, float f2) {
                setScreen(new GameRoom(g, p));
            }
        });

        host.addListener(new ClickListener() {
            public void clicked(InputEvent inputEvent, float f, float f2) {
                GameRoom r = new GameRoom(g, p);
                r.serverMode = 1;
                Net.handler = r;
                setScreen(r);
            }
        });

        join.addListener(new ClickListener() {
            public void clicked(InputEvent inputEvent, float f, float f2) {
                d.show(stage);
            }
        });
    }
}
