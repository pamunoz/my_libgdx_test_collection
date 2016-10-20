package com.udacity.gamedev.icicles;

import com.badlogic.gdx.Game;

import static com.udacity.gamedev.icicles.Constants.*;


public class IciclesGame extends Game {

	// call setScreen() with a new IciclesScreen()
	@Override
	public void create() {
		setScreen(new IciclesScreen(Difficulty.EASY));
	}
}