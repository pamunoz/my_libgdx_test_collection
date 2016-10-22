package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Snake {

    public static final String TAG = Snake.class.getName();

    Viewport viewport;

    Vector2 position;

    public int numberOfSegments;

    public Snake(Viewport viewport) {
        this.viewport = viewport;
        numberOfSegments = 1;
        init();
    }

    public void init() {
        position = new Vector2(0, 0);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            position.x -= delta * Constants.SNAKE_MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            position.x += delta * Constants.SNAKE_MOVEMENT_SPEED;
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            position.y += delta * Constants.SNAKE_MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            position.y -= delta * Constants.SNAKE_MOVEMENT_SPEED;
        }
        // ensure the head of the snake is inside the screen
        ensureInBounds();
    }

    private void ensureInBounds() {
        // ensure the limits of the horizontal position
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x > viewport.getWorldWidth() - Constants.SNAKE_SEGMENT_SIDE) {
            position.x = viewport.getWorldWidth() - Constants.SNAKE_SEGMENT_SIDE;
        }
        // ensure the limits of the vertical position
        if (position.y < 0) {
            position.y = 0;
        } else if (position.y > viewport.getWorldHeight() - Constants.SNAKE_SEGMENT_SIDE) {
            position.y = viewport.getWorldHeight() - Constants.SNAKE_SEGMENT_SIDE;
        }

    }

    public boolean eat(Vector2 foodPosition) {
        if (position.dst(foodPosition) > Constants.SNAKE_SEGMENT_SIDE / 3) {
            return true;
        } else {
            return false;
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.set(ShapeType.Filled);
        renderer.rect(position.x, position.y,
                Constants.SNAKE_SEGMENT_SIDE, Constants.SNAKE_SEGMENT_SIDE);
    }
}
