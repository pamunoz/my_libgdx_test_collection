package com.pfariasmunoz.drop_two.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pfariasmunoz.drop_two.DropTwo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop Two";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new DropTwo(), config);
	}
}
