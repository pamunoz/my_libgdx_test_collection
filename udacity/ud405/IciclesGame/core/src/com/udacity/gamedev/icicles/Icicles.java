package com.udacity.gamedev.icicles;

// TODO: Set up Icicles

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.icicles.Constants.Difficulty;

public class Icicles {

    public static final String TAG = Icicles.class.getName();

    // TODO: Add a Difficulty
    Difficulty difficulty;

    // Add counter for how many icicles have been dodged
    int dodgedIcicles;

    DelayedRemovalArray<Icicle> icicleList;

    Viewport viewport;

    // TODO: Accept a difficulty in the constructor
    public Icicles(Viewport viewport, Difficulty difficulty) {
        // TODO: Set difficulty
        this.difficulty = difficulty;
        this.viewport = viewport;
        init();
    }

    public void init() {
        icicleList = new DelayedRemovalArray<Icicle>(false, 100);
        // Set icicles dodged count to zero
        dodgedIcicles = 0;
    }

    public void update(float delta) {
        // TODO: Use the difficulty's spawn rate

        if (MathUtils.random() < delta * difficulty.spawnRate) {
            Vector2 newIciclePosition = new Vector2(
                    MathUtils.random() * viewport.getWorldWidth(),
                    viewport.getWorldHeight() + Constants.ICICLE_HEIGHT);

            icicleList.add(new Icicle(newIciclePosition));
        }
        for (Icicle icicle : icicleList) {
            icicle.update(delta);
        }

        // begin a removal session
        icicleList.begin();

        // Remove any icicle completely off the bottom of the screen
        for (int i = 0; i < icicleList.size; i++) {
            if (icicleList.get(i).position.y < -Constants.ICICLE_HEIGHT) {
                // Increment count of icicles dodged
                dodgedIcicles++;

                icicleList.removeIndex(i);
            }
        }

        // End removal session
        icicleList.end();

    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.ICICLE_COLOR);
        for (Icicle icicle : icicleList) {
            icicle.render(renderer);
        }
    }
}