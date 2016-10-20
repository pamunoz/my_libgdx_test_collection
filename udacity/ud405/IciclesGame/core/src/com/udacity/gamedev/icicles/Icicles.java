package com.udacity.gamedev.icicles;

// TODO: Set up Icicles

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Icicles {

    public static final String TAG = Icicles.class.getName();

    Array<Icicle> icicles;
    Viewport viewport;

    public Icicles(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        icicles = new Array<Icicle>(false, 100);
    }

    public void update(float delta) {
        if (MathUtils.random() < delta * Constants.ICICLE_SPAWNS_PER_SECOND) {
            Vector2 newIciclePosition = new Vector2(
                    MathUtils.random(viewport.getWorldWidth()),
                    viewport.getWorldHeight() + Constants.ICICLE_HEIGHT);

            icicles.add(new Icicle(newIciclePosition));
        }
        for (Icicle icicle : icicles) {
            icicle.update(delta);
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.ICICLE_COLOR);
        for (Icicle icicle : icicles) {
            icicle.render(renderer);
        }
    }
}