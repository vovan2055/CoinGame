package com.vovangames.coin.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Wall extends Actor {
    public Wall(float w, float h) {
        setSize(w, h);
    }

    public void collide(Actor victim) {
        Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle r2 = new Rectangle(victim.getX(), victim.getY(), victim.getWidth(), victim.getHeight());
        Rectangle r3 = new Rectangle(victim.getX(), victim.getY(), victim.getWidth(), victim.getHeight());
        Intersector.intersectRectangles(r, r2, r3);
        victim.setPosition(r3.x, r3.y);
    }
}
