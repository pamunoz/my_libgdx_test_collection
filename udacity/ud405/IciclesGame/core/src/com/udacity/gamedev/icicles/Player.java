package com.udacity.gamedev.icicles;

// Set up Player

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player {

    public static final String TAG = Player.class.getName();

    private static float speed = Constants.PLAYER_SPEED;

    Color color;

    // position (add constants to Constants.java first)
    Vector2 position;


    // viewport
    Viewport viewport;


    // constructor that accepts and sets the viewport, then calls init()

    public Player(Viewport viewport) {
        this.viewport = viewport;
        init();
    }


    // Tfunction that moves the character to the bottom center of the screen
    public void init() {

        position = new Vector2(viewport.getWorldWidth() / 2, Constants.PLAYER_HEAD_HEIGHT);
    }

    public void update(float delta) {
        color = Constants.PLAYER_COLOR;

        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            position.x -= delta * speed;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            position.x += delta * speed;
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            // accelerometer input = raw input / (gravity * sensitivity)
            float accelerometerInput = -Gdx.input.getAccelerometerY() / (Constants.ACCELEROMETER_SENSITIVITY * Constants.ACCELERATION_OF_GRAVITY);

            // Use the accelerometer input to move the player
            position.x += -delta * accelerometerInput * speed;
        }
        ensureInBounds();
    }

    // function that accepts a ShapeRenderer and does the actual drawing
    public void render(ShapeRenderer renderer) {
        Vector2 playerNeckTop = new Vector2(position.x, position.y - Constants.PLAYER_HEAD_RADIUS);
        Vector2 playerNeckBottom = new Vector2(position.x, position.y - 3.5f * Constants.PLAYER_HEAD_RADIUS);

        Vector2 armTop = new Vector2(position.x, playerNeckTop.y - (Constants.PLAYER_HEAD_RADIUS / 2));
        Vector2 armBottom = new Vector2(armTop.x - (Constants.PLAYER_HEAD_RADIUS / 2), armTop.y - (Constants.PLAYER_HEAD_RADIUS * 3));

        Vector2 legTop = new Vector2(position.x, playerNeckBottom.y);
        Vector2 legBottom = new Vector2(position.x - (Constants.PLAYER_HEAD_RADIUS / 2), 0.0f);

        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.set(ShapeType.Filled);
        // head
        renderer.circle(position.x, position.y, Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_HEAD_SEGMENTS);
        // body
        renderer.rectLine(playerNeckTop.x, playerNeckTop.y,
                          playerNeckBottom.x, playerNeckBottom.y,
                          Constants.PLAYER_LIMB_WIDTH);
        // left arm
        renderer.rectLine(armTop.x, armTop.y,
                          armBottom.x, armBottom.y,
                          Constants.PLAYER_LIMB_WIDTH);
        // right arm
        renderer.rectLine(armTop.x, armTop.y,
                armBottom.x + Constants.PLAYER_HEAD_RADIUS, armBottom.y,
                Constants.PLAYER_LIMB_WIDTH);

        // left leg
        renderer.rectLine(legTop.x, legTop.y,
                legBottom.x, legBottom.y,
                Constants.PLAYER_LIMB_WIDTH);
        // right leg
        renderer.rectLine(legTop.x, legTop.y,
                legBottom.x + Constants.PLAYER_HEAD_RADIUS, legBottom.y,
                Constants.PLAYER_LIMB_WIDTH);
    }

    private void ensureInBounds() {
        if (position.x > viewport.getWorldWidth() - Constants.PLAYER_HEAD_RADIUS) {
            position.x = viewport.getWorldWidth() - Constants.PLAYER_HEAD_RADIUS;
        }

        if (position.x < 0 + Constants.PLAYER_HEAD_RADIUS) {
            position.x = Constants.PLAYER_HEAD_RADIUS;
        }
    }

    public boolean hitByIcicle(Icicles icicles) {
        boolean isHit = false;

        for (Icicle icicle : icicles.icicles) {
            if (icicle.position.dst(position) < Constants.PLAYER_HEAD_RADIUS) {
                isHit = true;
            }
        }
        return  isHit;
    }

    public void changeColor() {
        color = Constants.PLAYER_HIT_COLOR;
    }
}