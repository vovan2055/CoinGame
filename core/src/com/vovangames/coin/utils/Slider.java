package com.vovangames.coin.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Slider extends Actor {

    public Sprite sprite;
    public Array<Cube> players;
    public Vector2 pushDir;

    public Slider(Texture texture) {
        sprite = new Sprite(texture);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        players = new Array<>();
        pushDir = new Vector2(0, 5);
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
        sprite.setPosition(f, f2);
    }

    @Override
    public void rotateBy(float angle) {
        super.rotateBy(angle);
        sprite.rotate(angle);
        pushDir.rotateDeg(angle);
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        sprite.setRotation(degrees);
        pushDir.setAngleDeg(degrees);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch, parentAlpha);

        for (Cube player : players) {
            if (player.collidesWith(this)) player.moveBy(pushDir.x, pushDir.y);
        }
    }
}
