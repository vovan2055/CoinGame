package com.vovangames.coin.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import java.io.PrintStream;

public class Cube extends Actor {
    public boolean bullet = false;
    public float bulletLife = ((float) 0);
    public Cube bulletTarget;
    public Array<Cube> bulletTargets;
    public boolean multipleTargets;
    public Sprite sprite;
    public ParticleSystem system;
    public Vector2 velocity;

    public Cube(Texture texture) {

        sprite = new Sprite(texture);
        system = new ParticleSystem(texture);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void setBulletTarget(Cube cube, float srcAngle) {
        Vector2 vector2;
        this.bullet = true;
        this.bulletTarget = cube;
        this.velocity = new Vector2(5, 0).rotateDeg(srcAngle);
    }

    public void setBulletTargets(Array<Cube> array, float srcAngle) {
        Vector2 vector2;
        bullet = true;
        multipleTargets = true;
        bulletTargets = array;
        velocity = new Vector2(5, 0).rotateDeg(srcAngle);
    }

    public void setBulletTargets(float srcAngle, Cube... targets) {
        Vector2 vector2;
        bullet = true;
        multipleTargets = true;
        bulletTargets = new Array<>();
        for (Cube c : targets) {
            bulletTargets.add(c);
        }
        velocity = new Vector2(5, 0).rotateDeg(srcAngle);
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        sprite.setSize(w, h);
        sprite.setOriginCenter();
    }

    @Override
    public void setPosition(float f, float f2) {
        super.setPosition(f, f2);
    }

    @Override
    public void rotateBy(float angle) {
        super.rotateBy(angle);
        this.sprite.rotate(angle);
    }

    @Override
    public void setRotation(float f) {

        super.setRotation(f);
        this.sprite.setRotation(f);
    }

    public void kill() {
        System.out.println(getName() + " was destroyed");
    }

    public void resetBullet() {
        bulletLife = (float) 0;
        bullet = false;
        multipleTargets = false;
        remove();
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        sprite.translate(x, y);
    }

    public boolean collidesWith(Actor actor) {
        Rectangle r1 = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle r2 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        return r1.overlaps(r2);
    }

    @Override
    public void draw(Batch batch, float f) {
        this.sprite.setPosition(getX(), getY());
        this.sprite.draw(batch);
        this.system.posX = (int) (getX() + (getWidth() / ((float) 4)));
        this.system.posY = (int) (((float) Gdx.graphics.getHeight()) - (getY() + (getHeight() / ((float) 4))));
        this.system.render(batch, Gdx.graphics.getDeltaTime());
        if (this.bullet) {
            this.bulletLife += Gdx.graphics.getDeltaTime();
            moveBy(velocity.x, velocity.y);
            if (this.multipleTargets) {
                for (Cube cube : this.bulletTargets) {
                    if (collidesWith(cube)) {
                        cube.kill();
                        resetBullet();
                    }
                }
            } else if (collidesWith(bulletTarget)) {
                bulletTarget.kill();
                resetBullet();
            }
            if (bulletLife >= 3) {
                resetBullet();
            }
        }
    }
}
