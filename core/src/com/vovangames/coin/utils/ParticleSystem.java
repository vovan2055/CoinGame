package com.vovangames.coin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import java.util.Random;

public class ParticleSystem extends Actor implements Disposable {
    static int buffersize = 60;
    public float angle = ((float) 0);
    public Batch batch;

    float cd = 0;
    public boolean fixedAngle = false;
    public float lifetime = 1;
    public Array<Particle> particles;
    public int posX = 0;
    public int posY = 0;
    public int size = 30;
    Sprite sprite;
    Texture texture;

    public ParticleSystem(Texture texture) {
        this.particles = new Array<>();

        sprite = new Sprite(texture);
        this.texture = texture;
        cd = 0;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        batch.dispose();
        particles = null;
        texture.dispose();
        sprite = null;
        remove();
    }

    public void render(Batch batch, float delta) {
        cd += delta;
        if (this.particles.size < buffersize && cd >= (1.0f / (buffersize *  2))) {


            Particle particle = new Particle(this, new Sprite(texture));
            if (this.fixedAngle) {
                particle.endAngle = angle;
                particle.startAngle = angle;
            }
            particle.startSize = size;
            particles.add(particle);
            cd = (float) 0;
        }
        for (Particle p : this.particles) {
            p.s.translate(p.dir.x, p.dir.y);
            p.s.setRotation(MathUtils.lerp(p.startAngle, p.endAngle, p.life / this.lifetime));
            p.s.setSize(MathUtils.lerp(p.startSize, p.endSize, p.life / this.lifetime), MathUtils.lerp(p.startSize, p.endSize, p.life / this.lifetime));
            p.s.draw(batch, p.alpha);
            p.alpha = MathUtils.lerp(p.startAlpha, p.endAlpha, p.life);
            p.size = MathUtils.lerp(p.startSize, p.endSize, p.life);
            p.angle = (float) (((double) p.angle) + 0.01d);
            p.life += delta;
            if (p.life > this.lifetime) {
                particles.removeValue(p, true);
            }
        }
    }

    public void render(float delta) {
        cd += delta;
        if (this.particles.size < buffersize && cd >= (1.0f / (buffersize *  2))) {


            Particle particle = new Particle(this, new Sprite(texture));
            if (this.fixedAngle) {
                particle.endAngle = angle;
                particle.startAngle = angle;
            }
            particle.startSize = size;
            particles.add(particle);
            cd = (float) 0;
        }
        for (Particle p : this.particles) {
            p.s.translate(p.dir.x, p.dir.y);
            p.s.setRotation(MathUtils.lerp(p.startAngle, p.endAngle, p.life / this.lifetime));
            p.s.setSize(MathUtils.lerp(p.startSize, p.endSize, p.life / this.lifetime), MathUtils.lerp(p.startSize, p.endSize, p.life / this.lifetime));
            p.s.draw(batch, p.alpha);
            p.alpha = MathUtils.lerp(p.startAlpha, p.endAlpha, p.life);
            p.size = MathUtils.lerp(p.startSize, p.endSize, p.life);
            p.angle = (float) (((double) p.angle) + 0.01d);
            p.life += delta;
            if (p.life > this.lifetime) {
                particles.removeValue(p, true);
            }
        }
    }

    class Particle {
        public float alpha = ((float) 1);
        public float angle;
        public float colorRandom = 0.15f;
        public Vector2 dir;
        public float endAlpha;
        public float endAngle = 0;
        public float endAngleRandom = 180.0f;
        public float endSize;
        public float life;
        Random r;
        public Sprite s;
        public float size = 100;
        public float startAlpha;
        public float startAngle;
        public float startSize;
        public float velocity = 1;
        public float velocityRandom = 2.0f;
        public float x;
        public float y;

        public Particle(ParticleSystem ps, Sprite sprite) {

            this.dir = new Vector2((float) 1, (float) 1);
            this.life = (float) 0;

            r = new Random();
            s = sprite;
            x = ps.posX;
            y = Gdx.graphics.getHeight() - ps.posY;
            s.setPosition(x, y);
            velocity += MathUtils.random(-this.velocityRandom, this.velocityRandom);
            startAngle = 0;
            endAngle += MathUtils.random(-this.endAngleRandom, this.endAngleRandom);
            startAlpha = (float) 1;
            endAlpha = (float) 1;
            startSize = 30;
            endSize = 0;
            dir.rotateDeg(MathUtils.random(-180, 180));
            dir.scl(velocity);
        }
    }
}
