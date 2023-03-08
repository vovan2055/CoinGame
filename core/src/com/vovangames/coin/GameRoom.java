package com.vovangames.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Connection;
import com.vovangames.coin.net.Net;
import com.vovangames.coin.net.NetHandler;
import com.vovangames.coin.utils.*;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameRoom extends ScreenAdapter implements NetHandler {
    public static int actorSize = 50;
    public static int barHeight = 80;
    public int serverMode = 0;
    Camera cam;
    Cube coin;
    Image coinIndicator;
    ProgressBar coins;
    ProgressBar.ProgressBarStyle cs;
    public CoinGame g;
    ProgressBar health;
    private int hp = 100;
    private float shootCd = 0;
    ProgressBar.ProgressBarStyle hs;
    String ip = "0.0.0.0";
    boolean isAttacked = false;
    boolean isBig = false;
    Label l;
    InputMultiplexer m;
    public Platform platform;
    Cube player;
    private int score = 0;
    Cube sharp;
    TextButton shoot;
    Stage stage;
    TimerTask t;
    Timer timer;
    Touchpad touchpad;
    Touchpad.TouchpadStyle ts;
    Stage ui;
    int id;
    boolean isConnected = false;

    @Override
    public void hide() {
    }

    @Override
    public void resume() {
    }

    public GameRoom(CoinGame coinGame, Platform platform) {
        this.g = coinGame;
        this.platform = platform;
    }

    public void setupGui() {
        ui = new Stage();
        Platform platform2 = this.platform;
        if (platform2 == Platform.ANDROID) {
            touchpad = new Touchpad((float) 0, ts);
            this.touchpad.setSize(300, 300);
            this.touchpad.setColor((float) 1, (float) 1, (float) 1, 0.4f);

            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
            style.font = new BitmapFont();
            style.fontColor = Color.WHITE;
            style.down = new TextureRegionDrawable(new TextureRegion(new Texture("cparticle.png")));
            shoot = new TextButton("shoot", style);
            shoot.setSize((float) 150, (float) 100);
            shoot.setPosition((float) (((int) (((double) Gdx.graphics.getWidth()) * 0.75d)) - 75), (float) 100);
            shoot.addListener(new ClickListener() {
                public void clicked(InputEvent inputEvent, float x, float y) {
                    shoot();
                }
            });
            ui.addActor(shoot);
            ui.addActor(touchpad);
        } else if (platform == Platform.DESKTOP) {
            //TODO
        }

        health = new ProgressBar( 0, 100, 0.1f, false, hs);
        health.setPosition((float) 0, (float) ((Gdx.graphics.getHeight() - barHeight) - 20));
        health.setValue(hp);
        barHeight = (int) this.health.getMinHeight();
        health.setSize((float) Gdx.graphics.getWidth(), (float) barHeight);

        coins = new ProgressBar(0, 100, 0.1f, false, cs);
        coins.setPosition((float) 0, (this.health.getY() - ((float) barHeight)) - ((float) 20));
        coins.setSize((float) Gdx.graphics.getWidth(), (float) barHeight);
        coins.setValue((float) this.score);

        l = new Label("ip", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        l.setZIndex(4);
        l.setPosition((float) 0, ((float) Gdx.graphics.getHeight()) - l.getHeight());
        ui.addActor(l);
        ui.addActor(health);
        ui.addActor(this.coins);

        m = new InputMultiplexer(stage, ui);
        Gdx.input.setInputProcessor(m);
    }

    public void shoot() {
        if (shootCd >= 2f) {
            shootCd = 0;
            BulletSpawner.spawnBullets(BulletSpawner.SpawnType.SINGLE, stage, player, player.sprite.getTexture(), 5, player.getRotation(), 5, sharp);
        }
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);

        player = new Cube(new Texture(Gdx.files.internal("player.png")));
        player.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        player.setSize((float) actorSize, (float) actorSize);
        player.setOrigin((float) (actorSize / 2), (float) (actorSize / 2));

        sharp = new Cube(new Texture(Gdx.files.internal("sharp.png"))) {
            @Override
            public void kill() {
                setPosition(MathUtils.random(Gdx.graphics.getWidth()), MathUtils.random(Gdx.graphics.getHeight()));
            }
        };
        sharp.setPosition((float) 0, (float) 0);
        sharp.setSize((float) actorSize, (float) actorSize);
        sharp.setOrigin((float) (actorSize / 2), (float) (actorSize / 2));
        coin = new Cube(new Texture(Gdx.files.internal("cparticle.png")));
        sharp.setUserObject("");
        coin.setPosition((float) 0, (float) (Gdx.graphics.getHeight() - 50));
        coin.setSize((float) actorSize, (float) actorSize);
        coin.setOrigin((float) (actorSize / 2), (float) (actorSize / 2));
        coin.setUserObject("");

        coinIndicator = new Image(new Texture(Gdx.files.internal("cparticle.png")));
        coinIndicator.setSize((float) (actorSize / 2), (float) (actorSize / 2));
        coinIndicator.setOrigin(coinIndicator.getX() + actorSize / 4f, -actorSize);

        ts = new Touchpad.TouchpadStyle();
        ts.background = ts.knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("touchpad.png"))));
        hs =new ProgressBar.ProgressBarStyle();
        cs = new ProgressBar.ProgressBarStyle();
        TextureAtlas atlas = new TextureAtlas();

        Texture tex = new Texture(Gdx.files.internal("bars.png"));
        atlas.addRegion("hb", tex, 0, 0, 32, 32);
        atlas.addRegion("hk", tex, 32, 0, 32, 32);
        atlas.addRegion("ck", tex, 64, 0, 32, 32);
        atlas.addRegion("cb", tex, 96, 0, 32, 32);
        hs.background = new TextureRegionDrawable(new TextureRegion(atlas.findRegion("hb")));
        hs.knobBefore = new TextureRegionDrawable(new TextureRegion(atlas.findRegion("hk")));
        cs.background = new TextureRegionDrawable(new TextureRegion(atlas.findRegion("cb")));
        cs.knobBefore = new TextureRegionDrawable(new TextureRegion(atlas.findRegion("ck")));
        stage.addActor(player);
        stage.addActor(sharp);
        stage.addActor(coin);
        stage.addActor(coinIndicator);
        Slider s = new Slider(player.sprite.getTexture());
        Array<Cube> arr = new Array<>();
        arr.add(player);
        s.players = arr;
        s.setPosition(100, 300);
        s.setSize(100, 500);
        s.pushDir = new Vector2(0, 5);
        stage.addActor(s);
        cam = stage.getCamera();
        timer =  new Timer();
        t = new TimerTask() {
            @Override
            public void run() {
                isAttacked = false;
            }
        };
        this.timer.scheduleAtFixedRate(t, 10000, 10000);
        setupGui();
        health.setValue(100);
        Array<Actor> load = CMapFileLoader.load(Gdx.files.internal("outMap.cmap"), player.sprite.getTexture());
        for (Actor a : load) {
            stage.addActor(a);
        }

        if (serverMode == 1) Net.initServer();
        else if (serverMode == 2) Net.initClient();

    }

    @Override
    public void render(float delta) {
        coin.rotateBy(250 * delta);
        shootCd += delta;
        coinIndicator.setPosition(player.getX(Align.center) - ((float) (actorSize / 4)), player.getY(Align.center) + ((float) actorSize));
        coinIndicator.setRotation(new Vector2(coin.getX(), coin.getY()).sub(player.getX(), player.getY()).angleDeg() - 90);
        readInput();
        if (isAttacked) {
            sharp.moveBy((player.getX() - sharp.getX()) * delta, (player.getY(Align.center) - sharp.getY(Align.center)) * delta);
        }
        if (coin.collidesWith(player)) {
            if (isBig) {
                score += 4;
            }
            coin.setX(new Random().nextInt(Gdx.graphics.getWidth() - actorSize));

            coin.setY(new Random().nextInt(Gdx.graphics.getHeight() - actorSize));
            isAttacked = true;
            score++;

            if (MathUtils.randomBoolean(0.05f)) {
                coin.setSize(actorSize * 3, actorSize * 3);
                isBig = true;
                coin.setOrigin(Align.center);
                coin.setColor(Color.GREEN);
            } else {
                coin.setSize(actorSize, actorSize);
                coin.setOrigin(Align.center);
                coin.setColor(Color.WHITE);
                isBig = false;
            }
            coins.setValue(score);
        }
        if (sharp.collidesWith(player)) {
            sharp.kill();
            sharp.setSize(this.sharp.getWidth() + ((float) 5), this.sharp.getHeight() + ((float) 5));


            hp -= new Random().nextInt(5);
            health.setValue(hp);
            if (hp <= 0) {
                System.out.println("you lose");
                Gdx.app.exit();
            }
        }

        cam.position.lerp(new Vector3(this.player.getX(), this.player.getY(), (float) 0), 0.05f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f);
        l.setText(this.player.getName());

        if (serverMode == 1) {
            Net.UpdatePlayer p = new Net.UpdatePlayer();
            p.id = 0;
            p.x = player.getX();
            p.y = player.getY();
            Net.server.server.sendToAllUDP(p);
        }
        else if (serverMode == 2 && isConnected) {
            Net.UpdatePlayer p = new Net.UpdatePlayer();
            p.id = id;
            p.x = player.getX();
            p.y = player.getY();
            Net.client.client.sendUDP(p);
        }

        stage.draw();
        ui.draw();
    }

    public void readInput() {
        if (platform == Platform.ANDROID) {
            if (this.touchpad.isTouched()) {

                player.setRotation(new Vector2(this.touchpad.getKnobPercentX(), this.touchpad.getKnobPercentY()).angleDeg());
                player.moveBy(this.touchpad.getKnobPercentX() * ((float) 10), this.touchpad.getKnobPercentY() * ((float) 10));
            }
        } else if (platform == Platform.DESKTOP) {
            handleKeyboard();
        }
    }

    public void handleKeyboard() {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) move(0, 10);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) move(-10, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.S)) move(0, -10);
            if (Gdx.input.isKeyPressed(Input.Keys.D)) move(10, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) shoot();

    }

    public void move(float x, float y) {
        player.moveBy(x, y);
        for (Actor a : stage.getActors()) {
            while (player.collidesWith(a) && a != player) {
                if (a instanceof Slider) {
                    player.moveBy(((Slider) a).pushDir.x * Gdx.graphics.getDeltaTime(), ((Slider) a).pushDir.y * Gdx.graphics.getDeltaTime());
                    break;
                }
                else if (a.getUserObject() != null) break;
                else player.moveBy(-x * 0.1f, -y * 0.1f);
            }
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        stage.getCamera().viewportWidth = width;
        stage.getCamera().viewportHeight = height;
        setupGui();
    }

    @Override
    public void dispose() {
        stage.dispose();
        ui.dispose();
        if (serverMode == 2) {
            Net.client.client.stop();
            Net.client.client.close();
            try {
                Net.client.client.dispose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("client disposed");
        }
        if (serverMode == 1) {
            Net.server.server.stop();
            Net.server.server.close();
            try {
                Net.server.server.dispose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("server disposed");
        }
    }

    @Override
    public void receive(Object o) {
        if (o instanceof Net.NewPlayer) {
            Cube c = new Cube(player.sprite.getTexture());
            c.setSize(actorSize, actorSize);
            c.setName(String.valueOf(((Net.NewPlayer) o).id));
            stage.addActor(c);
        } else if (o instanceof Net.UpdatePlayer) {
            try {
                Cube c = stage.getRoot().findActor(String.valueOf(((Net.UpdatePlayer) o).id));
                if (c == null) {
                    c = new Cube(player.sprite.getTexture());
                    c.setSize(actorSize, actorSize);
                    c.setName(String.valueOf(((Net.UpdatePlayer) o).id));
                    stage.addActor(c);
                }
                c.setX(((Net.UpdatePlayer) o).x);
                c.setY(((Net.UpdatePlayer) o).y);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else if (o instanceof Net.DeletePlayer) {
            stage.getRoot().findActor(String.valueOf(((Net.DeletePlayer) o).id)).remove();
        }
    }

    @Override
    public void connect(Connection c) {
        if (serverMode == 2) {
            id = c.getID();

            Net.NewPlayer n = new Net.NewPlayer();
            n.id = id;
            Net.client.client.sendUDP(n);
            System.out.println("sent new player packet");
            isConnected = true;

        }
    }

    @Override
    public void disconnect(Connection c) {
        if (serverMode == 2) {
            Net.client.client.stop();
            Net.client.client.close();
            try {
                Net.client.client.dispose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.setScreen(new MainMenu(g));
        }
    }
}
