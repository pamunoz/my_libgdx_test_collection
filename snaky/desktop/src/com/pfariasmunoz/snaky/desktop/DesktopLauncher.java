package com.pfariasmunoz.snaky.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pfariasmunoz.snaky.SnakeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Snake Game";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new SnakeGame(), config);
	}
}
