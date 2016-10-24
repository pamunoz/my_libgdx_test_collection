package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Pablo Farias on 24-10-16.
 */

public class Food {
    private Vector2 mPosition;
    private Viewport mViewport;
    private float mGridLength;
    private int mColumns;
    private int mRows;

    public Food(Viewport mViewport, float mGridLength) {
        this.mViewport = mViewport;
        this.mGridLength = mGridLength;
        mColumns = MathUtils.floor(mViewport.getWorldWidth() / mGridLength);
        mRows = MathUtils.floor(mViewport.getWorldHeight() / mGridLength);
        init();
    }

    /**
     * initializes the position of the food object inside the grid
     */
    private void init() {
        mPosition.x = MathUtils.floor(MathUtils.random(mColumns));
        mPosition.y = MathUtils.floor(MathUtils.random(mRows));
        mPosition.scl(mGridLength);
        ensureInBounds();
    }

    /**
     * ensure that the position of the food is inside the grid
     */
    private void ensureInBounds() {
        if (mPosition.x > mColumns * mGridLength - mGridLength) {
            mPosition.x = mColumns * mGridLength - mGridLength;
        } else if (mPosition.x < 0) {
            mPosition.x = 0;
        }
        if (mPosition.y > mRows * mGridLength - mGridLength) {
            mPosition.y = mRows * mGridLength - mGridLength;
        } else if (mPosition.y < 0) {
            mPosition.y = 0;
        }
    }

    /**
     * reset the position of the food to a random location inside the grid
     */
    public void resetPosition() {
        init();
    }

    /**
     * draw the food object to the screen
     * @param renderer the ShapeRenderer in charge of the drawing
     */
    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.rect(mPosition.x, mPosition.y, mGridLength, mGridLength);
    }

    public Vector2 getPosition() {
        return mPosition;
    }
}
