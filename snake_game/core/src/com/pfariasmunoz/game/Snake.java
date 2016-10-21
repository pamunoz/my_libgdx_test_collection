package com.pfariasmunoz.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Pablo Farias on 20-10-16.
 */

public class Snake {

    public static final String TAG = Snake.class.getName();

    int secondsPassed;
    Array<SnakeSegment> segments;
    Viewport viewport;

    public Snake(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        segments = new Array<SnakeSegment>();
        secondsPassed = 0;
    }

    public void update(float delta) {
       segments.add(new SnakeSegment(new Vector2(
               viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2)));
        for (SnakeSegment segment : segments) {
            segment.update(delta);
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        for (SnakeSegment segment : segments) {
            segment.render(renderer);
        }
    }


}
