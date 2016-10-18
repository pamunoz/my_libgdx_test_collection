package com.pfariasmunoz.game;

import com.badlogic.gdx.Game;

public class DemoGame extends Game {

    @Override
    public void create() {
        setScreen(new FallingObjectsScreen());

    }
}