package com.pfariasmunoz.camerademo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pfariasmunoz.camerademo.CameraDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Camera Demo";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new CameraDemo(), config);
	}
}
