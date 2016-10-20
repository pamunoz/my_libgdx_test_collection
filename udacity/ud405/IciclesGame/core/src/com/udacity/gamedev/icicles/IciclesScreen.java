package com.udacity.gamedev.icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class IciclesScreen implements Screen {

    public static final String TAG = IciclesScreen.class.getName();


    // ExtendViewport
    ExtendViewport iciclesViewport;

    // ShapeRenderer
    ShapeRenderer renderer;

    // Icicle

    Player player;
    Icicles icicles;


    @Override
    public void show() {
        // Initialize the iciclesViewport using the world size constant
        iciclesViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        // Initialize the ShapeRenderer
        renderer = new ShapeRenderer();

        // Set autoShapeType(true) on the ShapeRenderer
        renderer.setAutoShapeType(true);

        // Icicle in the middle of the world

        player = new Player(iciclesViewport);

        icicles = new Icicles(iciclesViewport);
    }

    @Override
    public void resize(int width, int height) {
        // the iciclesViewport updates correctly
        iciclesViewport.update(width, height, true);
        player.init();
        icicles.init();
    }

    @Override
    public void dispose() {
        
    }


    @Override
    public void render(float delta) {

        icicles.update(delta);

        player.update(delta);

        if (player.hitByIcicle(icicles)) {
            player.changeColor();
            icicles.init();
        }


        // the iciclesViewport
        iciclesViewport.apply(true);

        // screen to the background color
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // ShapeRenderer's projection matrix
        renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);

        // Draw the Icicle
        renderer.begin();
        //icicle.render(renderer);
        icicles.render(renderer);
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