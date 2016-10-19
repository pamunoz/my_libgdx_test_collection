package com.udacity.gamedev.icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class IciclesScreen implements Screen {

    public static final String TAG = IciclesScreen.class.getName();


    // ExtendViewport
    ExtendViewport iciclesViewport;

    // ShapeRenderer
    ShapeRenderer renderer;

    // Icicle
    Icicle icicle;

    Player player;


    @Override
    public void show() {
        // Initialize the iciclesViewport using the world size constant
        iciclesViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        // Initialize the ShapeRenderer
        renderer = new ShapeRenderer();

        // Set autoShapeType(true) on the ShapeRenderer
        renderer.setAutoShapeType(true);

        // Icicle in the middle of the world
        icicle = new Icicle(new Vector2(Constants.WORLD_SIZE / 2, Constants.WORLD_SIZE / 2));

        player = new Player(iciclesViewport);
    }

    @Override
    public void resize(int width, int height) {
        // the iciclesViewport updates correctly
        iciclesViewport.update(width, height, true);
        player.init();

    }

    @Override
    public void dispose() {
        
    }


    @Override
    public void render(float delta) {

        player.update(delta);

        // the iciclesViewport
        iciclesViewport.apply(true);

        // screen to the background color
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // ShapeRenderer's projection matrix
        renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);

        // Draw the Icicle
        renderer.begin();
        icicle.render(renderer);
        player.render(renderer);
        renderer.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Dispose of the ShapeRenderer
    @Override
    public void hide() {
        renderer.dispose();
    }
}