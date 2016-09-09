package com.pfariasmunoz.gfs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pfariasmunoz.gfs.GFS;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Games from Scratch videos";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new GFS(), config);
	}
}
