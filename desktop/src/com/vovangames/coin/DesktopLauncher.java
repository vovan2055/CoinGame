package com.vovangames.coin;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1000, 1000);
		config.setWindowIcon("player.png");
		config.setTitle("CoinGame");
		MyGdxGame g = new MyGdxGame();
		g.p = Platform.DESKTOP;
		new Lwjgl3Application(g, config);
	}
}
