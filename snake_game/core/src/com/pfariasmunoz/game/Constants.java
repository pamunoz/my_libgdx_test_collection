package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Constants.java
 * Purpose: this class is for creating default values for the game.
 *
 * @author Pablo Muñoz.
 * @version 1.0 10/07/2016
 */

public class Constants {
    // Setting up the screen with its grid
    public static final float BLOCK_SIZE = 16;
    public static final float COLUMNS = 30;
    public static final float ROWS = 30;
    public static final float WORLD_WIDTH = BLOCK_SIZE * COLUMNS;
    public static final float WORLD_HEIGHT = BLOCK_SIZE * ROWS;
    public static final Color BACKGROUND_COLOR = new Color(0.3764705882f, 0.3764705882f, 0.3764705882f, 1.0f);

    public static final Vector2 RIGHT = new Vector2(Constants.BLOCK_SIZE, 0);
    public static final Vector2 LEFT = new Vector2(-Constants.BLOCK_SIZE, 0);
    public static final Vector2 UP = new Vector2(0, Constants.BLOCK_SIZE);
    public static final Vector2 DOWN = new Vector2(0, -Constants.BLOCK_SIZE);

    // Background colors
    public static final Color BG_COLOR_1 = new Color(0.063f, 0.063f, 0.18f, 1.0f);
    public static final Color BG_COLOR_2 = new Color(0.078f, 0.078f, 0.306f, 1.0f);

    // Apple colors
    public static final Color APPLE_COLOR_1 = new Color(1.0f, 0.769f, 0.78f, 1.0f);
    public static final Color APPLE_COLOR_2 = new Color(1.0f, 0.757f, 0.35f, 1.0f);
    public static final Color APPLE_COLOR_3 = new Color(0.761f, 0.592f, 0.82f, 1.0f);

    // snake colors
    public static final Color SNAKE_COLOR_1 = new Color(0.137f, 0.137f, 0.761f, 1.0f);
    public static final Color SNAKE_COLOR_2 = new Color(0.196f, 0.196f, 0.902f, 1.0f);

    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;

}
