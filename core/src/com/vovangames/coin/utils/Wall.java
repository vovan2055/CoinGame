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
        Circle c = new Circle(victim.getX(Align.center), victim.getY(Align.center), victim.getHeight() / 2);

        float closestX = c.x;
        float closestY = c.y;

        if (c.x < r.x) {
            closestX = r.x;
        } else if (c.x > r.x + r.width) {
            closestX = r.x + r.width;
        }

        if (c.y < r.y) {
            closestY = r.y;
        } else if (c.y > r.y + r.height) {
            closestY = r.y + r.height;
        }

       if (c.contains(closestX, closestY)) {
           Vector2 v = new Vector2(c.x - closestX,c.y - closestY);
           v.scl((v.len() / c.radius));
           victim.moveBy(v.x, v.y);
       }
    }
}
