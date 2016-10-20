package com.udacity.gamedev.icicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    // constant for the world size
    public static final float WORLD_SIZE = 10.0f;

    // constant for the background color of the world
    public static final Color BACKGROUND_COLOR = Color.BLUE;

    // constant for player head radius
    public static final float PLAYER_HEAD_RADIUS = 0.4f;

    // constant for player head height
    public static final float PLAYER_HEAD_HEIGHT = WORLD_SIZE / 4;

    // constant for player limb width
    public static final float PLAYER_LIMB_WIDTH = 0.1f;

    // constant for circle segments for the player's head
    public static final int PLAYER_HEAD_SEGMENTS = 20;

    // constant for the player's color
    public static final Color PLAYER_COLOR = Color.BLACK;

    // constant for the player's hit color
    public static final Color PLAYER_HIT_COLOR = Color.RED;

    // player movement speed
    public static final float PLAYER_SPEED = 10.0f;

    // Accelerometer sensitivity
    public static final float ACCELEROMETER_SENSITIVITY = 0.5f;

    // acceleration due to gravity (9.8)
    public static final float ACCELERATION_OF_GRAVITY = 9.8f;

    // constant for the height of the icicle
    public static final float ICICLE_HEIGHT = 1.0f;

    // constant for the width of the icicle
    public static final float ICICLE_WIDTH = 0.5f;

    // constant for the color of the icicles
    public static final Color ICICLE_COLOR = Color.WHITE;

    // constant for icicle acceleration
    public static final Vector2 ICICLE_ACCELERATION = new Vector2(0, -5.0f);

    // constant for icicle spawns per second
    public static final float ICICLE_SPAWNS_PER_SECOND = 10.0f;

    // Add screen reference size for scaling the HUD (480 works well)
    public static final float SCREEN_REFERENCE_SIZE = 480.0f;

    // Add constant for the margin between the HUD and screen edge
    public static final float HUD_MARGIN = 20.0f;
}
