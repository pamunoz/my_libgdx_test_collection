package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    // Setting up the screen with its grid
    public static final float BLOCK_SIZE = 16;
    public static final float COLUMNS = 20;
    public static final float ROWS = 20;
    public static final float WORLD_WIDTH = BLOCK_SIZE * COLUMNS;
    public static final float WORLD_HEIGHT = BLOCK_SIZE * ROWS;
    public static final Color BACKGROUND_COLOR = new Color(0.3764705882f, 0.3764705882f, 0.3764705882f, 1.0f);

    public static final Vector2 RIGHT = new Vector2(Constants.BLOCK_SIZE, 0);
    public static final Vector2 LEFT = new Vector2(-Constants.BLOCK_SIZE, 0);
    public static final Vector2 UP = new Vector2(0, Constants.BLOCK_SIZE);
    public static final Vector2 DOWN = new Vector2(0, -Constants.BLOCK_SIZE);

}
