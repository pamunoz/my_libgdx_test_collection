package com.udacity.gamedev.icicles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Icicle {

    public static final String TAG = Icicle.class.getName();

    Vector2 position;
    Vector2 velocity;

    public Icicle(Vector2 position) {
        this.position = position;
        velocity = new Vector2();
    }

    public void update(float delta) {
        // First scale a supplied vector, then add it to this vector
        velocity.mulAdd(Constants.ICICLE_ACCELERATION, delta);
        position.mulAdd(velocity, delta);
    }

    // render function that takes a ShapeRenderer
    public void render(ShapeRenderer renderer) {
        // ShapeRenderer's color
        renderer.setColor(Constants.ICICLE_COLOR);
        // ShapeType
        renderer.set(ShapeType.Filled);
        // Draw the icicle using the size constants
        renderer.triangle(
                position.x, position.y,
                position.x - Constants.ICICLE_WIDTH / 2, position.y + Constants.ICICLE_HEIGHT,
                position.x + Constants.ICICLE_WIDTH / 2, position.y + Constants.ICICLE_HEIGHT
        );
    }




}
