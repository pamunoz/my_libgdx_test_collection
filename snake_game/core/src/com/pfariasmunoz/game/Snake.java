package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Snake {

    public static final String TAG = Snake.class.getName();

    private Viewport mViewport;
    private Vector2 mPosition;
    Vector2 mVelocity;

    private float mGridLength;
    private int columns;
    private int rows;


    public Snake(Viewport viewport, float gridLength) {
        this.mViewport = viewport;
        this.mGridLength = gridLength;
        columns = MathUtils.floor(viewport.getWorldWidth() / gridLength);
        rows = MathUtils.floor(viewport.getWorldHeight() / gridLength);
        init();
    }

    public void init() {
        mPosition = new Vector2(0, 0);
        mVelocity = new Vector2(mGridLength, 0);
    }

    /**
     * Makes the snake turn according to the direction
     * @param direction the direction of turn.
     */
    private void turn(Vector2 direction) {
        mVelocity.x = direction.x;
        mVelocity.y = direction.y;
    }

    /**
     * Update the direction of the snake according to the keys pressed
     */
    public void update() {
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            turn(new Vector2(mGridLength, 0));
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            turn(new Vector2(-mGridLength, 0));
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            turn(new Vector2(0, mGridLength));
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            turn(new Vector2(0, -mGridLength));
        }
    }

    /**
     * Move the snake with its velocity
     */
    public void move() {
        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
        ensureInBounds();
    }

    /**
     * Limit the movement of the snake to the bounds of the screen
     */
    private void ensureInBounds() {
        // limit the movement of the snake in the horizontal position
        if (mPosition.x > columns * mGridLength - mGridLength) {
            mPosition.x = columns * mGridLength - mGridLength;
        } else if (mPosition.x < 0) {
            mPosition.x = 0;
        }
        // limits the movement of the snake in the vertical position
        if (mPosition.y > rows * mGridLength - mGridLength) {
            mPosition.y = rows * mGridLength - mGridLength;
        } else if (mPosition.y < 0) {
            mPosition.y = 0;
        }
    }

    /**
     * answers if the snake has eaten the food
      * @param foodPosition the position on the grid of the food
     * @return the answer if the snake has eaten.
     */
    public boolean hasEaten(Vector2 foodPosition) {
        if (mPosition.dst(foodPosition) < mGridLength / 2.0f) {
            return true;
        } else {
            return false;
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.rect(mPosition.x, mPosition.y, mGridLength, mGridLength);
    }


    // ========== Getters and Setters ==============

    public Viewport getmViewport() {
        return mViewport;
    }

    public void setmViewport(Viewport mViewport) {
        this.mViewport = mViewport;
    }

    public Vector2 getmPosition() {
        return mPosition;
    }

    public void setmPosition(Vector2 mPosition) {
        this.mPosition = mPosition;
    }

    public Vector2 getmVelocity() {
        return mVelocity;
    }

    public void setmVelocity(Vector2 mVelocity) {
        this.mVelocity = mVelocity;
    }

    public float getmGridLength() {
        return mGridLength;
    }

    public void setmGridLength(float mGridLength) {
        this.mGridLength = mGridLength;
    }
}
