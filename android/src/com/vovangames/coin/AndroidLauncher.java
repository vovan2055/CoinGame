package com.vovangames.coin;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onResume() {
		super.onResume();
		initialize(new MyGdxGame());
	}
}
