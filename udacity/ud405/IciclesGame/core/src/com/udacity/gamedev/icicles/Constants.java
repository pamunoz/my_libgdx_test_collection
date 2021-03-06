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

    // Create constants for difficulty labels ("Cold", "Colder", "Coldest")
    public static final String EASY_LABEL = "Cold";
    public static final String NORMAL_LABEL = "Colder";
    public static final String HARD_LABEL = "Coldest";

    // Create constants for the icicle spawn rates for the various difficulties
    public static final float EASY_SPAWN_RATE = 5.0f;
    public static final float NORMAL_SPAWN_RATE = 10.0f;
    public static final float HARD_SPAWN_RATE = 30.0f;

    // TODO: Add constants for the color of each difficulty select circle
    public static final Color EASY_DIFFICULTY_COLOR = new Color(0.0f, 0.502f, 1.0f, 1.0f);
    public static final Color NORMAL_DIFFICULTY_COLOR = new Color(0.4f, 0.7f, 1.0f, 1.0f);
    public static final Color HARD_DIFFICULTY_COLOR = new Color(0.8f, 0.89f, 1.0f, 1.0f);

    // TODO: Add constant for the size of the difficulty world
    public static final float DIFFICULTY_WORLD_SIZE = 480.0f;

    // TODO: Add constant for the radius of the difficulty select "buttons"
    public static final float DIFFICULTY_BUTTON_RADIUS = DIFFICULTY_WORLD_SIZE / 9;

    // TODO: Add constant for the scale of the difficulty labels (1.5 works well)
    public static final float DIFFICULTY_LABEL_SCALE = 1.5f;

    // TODO: Add Vector2 constants for the centers of the difficulty select buttons
    public static final Vector2 EASY_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE / 4, DIFFICULTY_WORLD_SIZE / 2);
    public static final Vector2 NORMAL_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE / 2, DIFFICULTY_WORLD_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE * 3 / 4, DIFFICULTY_WORLD_SIZE / 2);

    // Create Difficulty enum holding the spawn rate and label for each difficulty
    public enum Difficulty {
        EASY(EASY_SPAWN_RATE, EASY_LABEL),
        NORMAL(NORMAL_SPAWN_RATE, NORMAL_LABEL),
        HARD(HARD_SPAWN_RATE, HARD_LABEL);

        float spawnRate;
        String label;

        Difficulty(float spawnRate, String label) {
            this.spawnRate = spawnRate;
            this.label = label;
        }

    }
}
