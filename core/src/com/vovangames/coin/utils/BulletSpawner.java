package com.vovangames.coin.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

public class BulletSpawner {
    public  static  float bulletSize = 20;
    public static void spawnBullets(SpawnType type, Stage stage, Cube origin, Texture texture, int count, float angle,  float step, Cube... targets) {
        switch (type) {
            case SINGLE:
                Cube bullet = new Cube(texture);
                bullet.setSize(bulletSize, bulletSize);
                bullet.setPosition(origin.getX(Align.center), origin.getY(Align.center));
                bullet.setBulletTargets(angle, targets);
                stage.addActor(bullet);
                break;
            case FAN:
                for (float f = angle - step * (count / 2f); f < count; f += step) {
                    Cube b = new Cube(texture);
                    b.setSize(bulletSize, bulletSize);
                    b.setPosition(origin.getX(Align.center), origin.getY(Align.center));
                    b.setBulletTargets(angle + f, targets);
                    stage.addActor(b);
                }
                break;
            case RADIAL:
                for (float f = 0; f < 360; f += 360f / count) {
                    Cube b = new Cube(texture);
                    b.setSize(bulletSize, bulletSize);
                    b.setPosition(origin.getX(Align.center), origin.getY(Align.center));
                    b.setBulletTargets(angle + f, targets);
                    stage.addActor(b);
                }
                break;
        }
    }
    public static enum SpawnType {
        SINGLE, RADIAL, FAN
    }
}
