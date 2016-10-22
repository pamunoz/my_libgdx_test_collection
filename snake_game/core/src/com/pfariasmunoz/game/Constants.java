package com.pfariasmunoz.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Pablo Farias on 20-10-16.
 */

public class Constants {
    public static final float GAME_WORLD_HEIGHT = 360.0f;
    public static final float GAME_WORLD_WIDTH = 640.0f;
    public static final float SNAKE_SEGMENT_SIDE = GAME_WORLD_HEIGHT / 30.0f;
    public static final float SNAKE_MOVEMENT_SPEED = 1.0f;
    public static final Vector2 SNAKE_ACCELERATION = new Vector2(SNAKE_SEGMENT_SIDE, 0);
    public static final float ACCELERATION = SNAKE_SEGMENT_SIDE;
}
