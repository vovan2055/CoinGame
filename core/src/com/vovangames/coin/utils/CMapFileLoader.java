package com.vovangames.coin.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class CMapFileLoader {

    public static Array<Actor> load(FileHandle file, Texture defaultTexture) {
        String s = file.readString();
        String[] objects = s.split("\n");
        Array<Actor> actors = new Array<>();
        for (String object : objects) {
            String[] params = object.split(";");
            switch (params[0]) {
                case "WALL":
                    Wall w = new Wall(Float.parseFloat(params[3]), Float.parseFloat(params[4]));
                    w.setPosition(Float.parseFloat(params[1]), Float.parseFloat(params[2]));
                    actors.add(w);
                case "SLIDER":
                    Slider slider = new Slider(defaultTexture);
                    slider.setPosition(Float.parseFloat(params[1]), Float.parseFloat(params[2]));
                    slider.setSize(Float.parseFloat(params[3]), Float.parseFloat(params[4]));
                    actors.add(slider);
            }
        }
        return actors;
    }

}
