package com.vovangames.coin;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	@Override
	public void setApplicationLogger(ApplicationLogger applicationLogger) {
		super.setApplicationLogger(applicationLogger);
	}

	@Override
	public ApplicationLogger getApplicationLogger() {
		return super.getApplicationLogger();
	}

	@Override
	protected void onResume() {
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useGyroscope = false;
		config.useImmersiveMode = true;

		MyGdxGame g = new MyGdxGame();
		g.p = Platform.ANDROID;
		initialize(g, config);
		super.onResume();
	}

}
