package com.vovangames.coin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.vovangames.coin.Platform;
import com.vovangames.coin.utils.Cube;
import com.vovangames.coin.utils.ParticleSystem;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameRoom extends ScreenAdapter {
    public static int actorSize = 50;
    public static int barHeight = 80;
    public static int serverMode = 0;
    Label Coins;

    /* renamed from: Cs */
    public ParticleSystem Cs;

    /* renamed from: Hp */
    Label Hp;
    SpriteBatch batch;
    Cube bullet;
    CameraInputController c;
    Camera cam;
    Cube coin;
    Image coinIndicator;
    ProgressBar coins;
    float counter = ((float) 0);

    /* renamed from: cs */
    ProgressBar.ProgressBarStyle cs;

    /* renamed from: g */
    public MyGdxGame g;
    ProgressBar health;

    /* renamed from: hp */
    private int hp = 100;

    /* renamed from: hs */
    ProgressBar.ProgressBarStyle hs;

    /* renamed from: ip */
    String ip = "0.0.0.0";
    boolean isAttacked = false;
    boolean isBig = false;

    /* renamed from: l */
    Label l;

    /* renamed from: m */
    InputMultiplexer m;
    public Platform platform;
    Cube player;
    Vector2 playerpos;

    /* renamed from: ps */
    public ParticleSystem ps;
    private int score = 0;
    Cube sharp;
    TextButton shoot;
    Skin skin;

    /* renamed from: ss */
    public ParticleSystem ss;
    Stage stage;

    /* renamed from: t */
    TimerTask t;
    Thread thread;
    Timer timer;
    float touchX = ((float) 0);
    float touchY = ((float) 0);
    Touchpad touchpad;

    /* renamed from: ts */
    Touchpad.TouchpadStyle ts;

    /* renamed from: ui */
    Stage ui;

    /* renamed from: x */
    float x = ((float) 0);

    /* renamed from: y */
    float f270y = ((float) 0);

    @Override
    public void hide() {
    }

    @Override
    public void resume() {
    }

    public GameRoom(MyGdxGame myGdxGame, Platform platform) {
        this.g = myGdxGame;
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

                    if (bullet == null) {
                        bullet.setPosition(player.getX(), player.getY());
                        stage.addActor(bullet);
                        bullet.setBulletTarget(sharp, player.getRotation());
                    }
                }
            });
            ui.addActor(this.shoot);
            ui.addActor(this.touchpad);
        } else if (platform2 == Platform.DESKTOP) {
        }

        this.health = new ProgressBar( 0, 100, 0.1f, false, hs);
        this.health.setPosition((float) 0, (float) ((Gdx.graphics.getHeight() - barHeight) - 20));
        boolean value = this.health.setValue(hp);
        barHeight = (int) this.health.getMinHeight();
        this.health.setSize((float) Gdx.graphics.getWidth(), (float) barHeight);

        this.coins = new ProgressBar(0, 100, 0.1f, false, cs);
        this.coins.setPosition((float) 0, (this.health.getY() - ((float) barHeight)) - ((float) 20));
        this.coins.setSize((float) Gdx.graphics.getWidth(), (float) barHeight);
        boolean value2 = this.coins.setValue((float) this.score);


        l = new Label("ip", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        l.setZIndex(4);
        l.setPosition((float) 0, ((float) Gdx.graphics.getHeight()) - l.getHeight());
        ui.addActor(l);
        ui.addActor(health);
        ui.addActor(this.coins);

        m = new InputMultiplexer();
        m.addProcessor(this.stage);
        m.addProcessor(ui);
        Gdx.input.setInputProcessor(m);
    }
    @Override
    public void show() {


        stage = new Stage();
        stage.setDebugAll(true);

        player = new Cube(new Texture(Gdx.files.internal("player.png")));
        player.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        player.setSize((float) actorSize, (float) actorSize);
        player.setOrigin((float) (actorSize / 2), (float) (actorSize / 2));


        this.sharp = new Cube(new Texture(Gdx.files.internal("sharp.png"))) {
            @Override
            public void kill() {
                setPosition((float) MathUtils.random(Gdx.graphics.getWidth()), (float) MathUtils.random(Gdx.graphics.getHeight()));
            }
        };
        this.sharp.setPosition((float) 0, (float) 0);
        this.sharp.setSize((float) actorSize, (float) actorSize);
        this.sharp.setOrigin((float) (actorSize / 2), (float) (actorSize / 2));
        this.coin = new Cube(new Texture(Gdx.files.internal("cparticle.png")));
        this.coin.setPosition((float) 0, (float) (Gdx.graphics.getHeight() - 50));
        this.coin.setSize((float) actorSize, (float) actorSize);
        this.coin.setOrigin((float) (actorSize / 2), (float) (actorSize / 2));


        coinIndicator = new Image(new Texture(Gdx.files.internal("cparticle.png")));
        coinIndicator.setSize((float) (actorSize / 2), (float) (actorSize / 2));
        coinIndicator.setOrigin(coinIndicator.getX() + actorSize / 4f, -actorSize);


        bullet = new Cube(new Texture(Gdx.files.internal("cparticle.png")));
        bullet.setSize(30, 30);
        ts = new Touchpad.TouchpadStyle();





        ts.background = ts.knob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("touchpad.png"))));

        hs =new ProgressBar.ProgressBarStyle();

        cs = new ProgressBar.ProgressBarStyle();

        TextureAtlas textureAtlas2 = new TextureAtlas();

        Texture texture11 = new Texture(Gdx.files.internal("bars.png"));
        textureAtlas2.addRegion("hb", texture11, 0, 0, 32, 32);
        textureAtlas2.addRegion("hk", texture11, 32, 0, 32, 32);
        textureAtlas2.addRegion("ck", texture11, 64, 0, 32, 32);
        textureAtlas2.addRegion("cb", texture11, 96, 0, 32, 32);
        hs.background = new TextureRegionDrawable(new TextureRegion(textureAtlas2.findRegion("hb")));



        hs.knobBefore = new TextureRegionDrawable(new TextureRegion((TextureRegion) textureAtlas2.findRegion("hk")));
        ProgressBar.ProgressBarStyle progressBarStyle5 = cs;


        progressBarStyle5.background = new TextureRegionDrawable(new TextureRegion((TextureRegion) textureAtlas2.findRegion("cb")));



        cs.knobBefore = new TextureRegionDrawable(new TextureRegion(textureAtlas2.findRegion("ck")));
        stage.addActor(player);
        stage.addActor(sharp);
        stage.addActor(coin);
        stage.addActor(coinIndicator);
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
        ps = new ParticleSystem(new Texture(Gdx.files.internal("player.png")));
        ss = new ParticleSystem( new Texture(Gdx.files.internal("sharp.png")));
        Cs = new ParticleSystem(new Texture(Gdx.files.internal("cparticle.png")));
        ps.batch = this.stage.getBatch();
        Cs.batch = this.stage.getBatch();
        ss.batch = this.stage.getBatch();
        stage.addActor(ps);
        stage.addActor(ss);
        stage.addActor(Cs);
        ss.posX = (int) sharp.getX();
        ss.posY = (int) (Gdx.graphics.getHeight() - sharp.getY());
        ss.lifetime = 0.5f;
        ps.lifetime = 0.5f;
        Cs.lifetime = 2.0f;
        Cs.fixedAngle = true;
        Cs.angle = (float) 45;
        ss.setZIndex(0);
        ps.setZIndex(0);
        Cs.setZIndex(0);
        Cs.posX = (int) (this.coin.getX() + (((float) actorSize) / 2.0f));
        Cs.posY = (int) (((float) Gdx.graphics.getHeight()) - this.coin.getY());
    }

    @Override
    public void render(float delta) {
        this.coin.rotateBy(250 * delta);
        this.coinIndicator.setPosition(player.getX(Align.center) - ((float) (actorSize / 4)), player.getY(Align.center) + ((float) actorSize));
        Image image = this.coinIndicator;

        image.setRotation(new Vector2(this.coin.getX(), this.coin.getY()).sub(this.player.getX(), this.player.getY()).angleDeg() - 90);
        readInput();
        if (this.isAttacked) {
            this.sharp.moveBy((this.player.getX() - this.sharp.getX()) * delta * ((float) this.score), (this.player.getY() - this.sharp.getY()) * delta * ((float) this.score));
        }
        if (this.coin.collidesWith(this.player)) {
            if (this.isBig) {
                this.score += 4;
            }
            coin.setX(new Random().nextInt(Gdx.graphics.getWidth() - actorSize));

            coin.setY(new Random().nextInt(Gdx.graphics.getHeight() - actorSize));
            Cs.posX = (int) (this.coin.getX() + (((float) actorSize) / 2.0f));
            Cs.posY = (int) (((float) Gdx.graphics.getHeight()) - this.coin.getY());
            this.isAttacked = true;
            this.score++;

            if (new Random().nextInt(20) == 9) {
                this.coin.setSize((float) (actorSize * 3), (float) (actorSize * 3));
                this.isBig = true;
                this.coin.setColor(Color.GREEN);
            } else {
                this.coin.setSize((float) actorSize, (float) actorSize);
                this.coin.setColor(Color.WHITE);
                this.isBig = false;
            }
            boolean value = this.coins.setValue((float) this.score);
        }
        if (sharp.collidesWith(player)) {
            ss.size++;
            ss.lifetime += 1;
            this.sharp.kill();
            this.sharp.setSize(this.sharp.getWidth() + ((float) 5), this.sharp.getHeight() + ((float) 5));


            hp -= new Random().nextInt(5);
            health.setValue(hp);
            if (hp <= 0) {
                System.out.println("you lose");
                Gdx.app.exit();
            }
        }

        Vector3 lerp = this.cam.position.lerp(new Vector3(this.player.getX(), this.player.getY(), (float) 0), 0.05f);
        ps.posX = (int) (this.player.getX() + (((float) actorSize) / 4.0f));
        ps.posY = (int) ((((float) Gdx.graphics.getHeight()) - this.player.getY()) - (((float) actorSize) / 4.0f));
        ss.posX = (int) (this.sharp.getX() + (((float) actorSize) / 4.0f));
        ss.posY = (int) ((((float) Gdx.graphics.getHeight()) - this.sharp.getY()) - (((float) actorSize) / 4.0f));
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(16384);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, (float) 1);
        l.setText(this.player.getName());
        stage.draw();
        ui.draw();
    }

    public void readInput() {
        Vector2 vector2;
        Platform platform2 = this.platform;
        if (platform2 == Platform.ANDROID) {
            if (this.touchpad.isTouched()) {

                player.setRotation(new Vector2(this.touchpad.getKnobPercentX(), this.touchpad.getKnobPercentY()).angleDeg());
                player.moveBy(this.touchpad.getKnobPercentX() * ((float) 10), this.touchpad.getKnobPercentY() * ((float) 10));
            }
        } else if (platform2 == Platform.DESKTOP) {
            handleKeyboard();
        }
    }

    public void handleKeyboard() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) player.moveBy(0, 10);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) player.moveBy(-10,  0);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) player.moveBy(0, -10);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) player.moveBy(10, 0);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int i, int i2) {
        this.stage.getCamera().viewportWidth = (float) i;
        this.stage.getCamera().viewportHeight = (float) i2;
        setupGui();
    }

    @Override
    public void dispose() {
        stage.dispose();
        ui.dispose();
    }
}
