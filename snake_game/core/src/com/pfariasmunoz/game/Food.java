package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Food.java
 * Purpose: creates the food that the Snake eats which increases its
 * speed along with score of the player.
 *
 * @author Pablo Muñoz.
 * @version 1.0 10/07/2016
 */

public class Food {

    private Vector2 mFoodPosition;

    Viewport viewport;

    public Food(Viewport viewport) {
        this.viewport = viewport;
        mFoodPosition = new Vector2();
    }

    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(0.76f, 0.592f, 0.0823f, 1.0f);
        renderer.rect(mFoodPosition.x, mFoodPosition.y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
    }

    public void resetFoodPosition() {
        int x = MathUtils.floor(MathUtils.random(Constants.COLUMNS));
        int y = MathUtils.floor(MathUtils.random(Constants.ROWS));

        mFoodPosition = new Vector2(x, y);
        mFoodPosition.scl(Constants.BLOCK_SIZE);
    }

    public Vector2 getFoodPosition() {
        return mFoodPosition;
    }

}
