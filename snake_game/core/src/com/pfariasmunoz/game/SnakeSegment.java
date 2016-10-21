package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SnakeSegment {
    public static final String TAG = SnakeSegment.class.getName();

    Vector2 position;
    Vector2 velocity;

    public SnakeSegment(Vector2 position) {
        this.position = position;
        this.velocity = new Vector2();
    }

    public void update(float delta) {
        velocity.mulAdd(Constants.SNAKE_ACCELERATION, delta);
        position.mulAdd(velocity, delta);
    }

    public void render(ShapeRenderer renderer) {
        renderer.rect(position.x, position.y, Constants.SNAKE_SEGMENT_SIDE, Constants.SNAKE_SEGMENT_SIDE);
    }
}
