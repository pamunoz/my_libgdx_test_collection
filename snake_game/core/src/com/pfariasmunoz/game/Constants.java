package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Pablo Farias on 20-10-16.
 */

public class Constants {
    // World constants
    public static final float WORLD_SIZE = 16;
    public static final Color BACKGROUND_COLOR = Color.BLUE;

    // Mobs constants
    public static final float GRID_SIDE = 0.4f;
    public static final Color SNAKE_COLOR = Color.WHITE;

    public static final Color FOOD_COLOR = Color.RED;

    // Directions
    public static final Vector2 RIGHT = new Vector2(GRID_SIDE, 0);
    public static final Vector2 LEFT = new Vector2(-GRID_SIDE, 0);
    public static final Vector2 UP = new Vector2(0, GRID_SIDE);
    public static final Vector2 DOWN = new Vector2(0, -GRID_SIDE);

    public static final float DIFFICULTY_ACCELEROMETER = 2.0f;
    public static final float SNAKE_ACCELERATION = 1.1f;

    // HUD
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;
    public static final float HUD_MARGIN = 20.0f;

    public enum Direction {
        RIGHT, LEFT, UP, DONW
    }


    // Dificulty Screen
    public static final String EASY_LABEL = "Fácil";
    public static final String NORMAL_LABEL = "Normal";
    public static final String HARD_LABEL = "Difícil";

}
