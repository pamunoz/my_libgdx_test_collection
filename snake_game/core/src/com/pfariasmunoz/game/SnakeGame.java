package com.pfariasmunoz.game;

import com.badlogic.gdx.Game;

/**
 * SnakeGame.java
 * Purpose: this class is only to call the ScreenGame.
 *
 * @author Pablo Muñoz.
 * @version 1.0 10/07/2016
 */

public class SnakeGame extends Game {
	
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
}
