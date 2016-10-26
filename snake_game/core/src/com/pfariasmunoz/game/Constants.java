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
    private static final Vector2 RIGHT = new Vector2(GRID_SIDE, 0);
    private static final Vector2 LEFT = new Vector2(-GRID_SIDE, 0);
    private static final Vector2 UP = new Vector2(0, GRID_SIDE);
    private static final Vector2 DOWN = new Vector2(0, -GRID_SIDE);

    public static final float DIFFICULTY_ACCELEROMETER = 2.0f;
    public static final float SNAKE_ACCELERATION = 0.1f;

    // HUD
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;
    public static final float HUD_MARGIN = 20.0f;

    public static final int COLUMNS = MathUtils.floor(WORLD_SIZE / GRID_SIDE);

    public enum Direction {
        RIGHT(Constants.RIGHT),
        LEFT(Constants.LEFT),
        UP(Constants.UP),
        DOWN(Constants.DOWN);
        private Vector2 dir;
        Direction(Vector2 dir) {
            this.dir = dir;
        }

        public Vector2 getDir() {
            return dir;
        }


        @Override
        public String toString() {
            switch (this) {
                case RIGHT:
                    System.out.println("Direction Right: " + getDir());
                    break;
                case LEFT:
                    System.out.println("Direction Left: " + getDir());
                    break;
                case UP:
                    System.out.println("Direction Up: " + getDir());
                    break;
                case DOWN:
                    System.out.println("Direction Down: " + getDir());
            }
            return super.toString();
        }
    }


    // Dificulty Screen
    public static final String EASY_LABEL = "Fácil";
    public static final String NORMAL_LABEL = "Normal";
    public static final String HARD_LABEL = "Difícil";

}
