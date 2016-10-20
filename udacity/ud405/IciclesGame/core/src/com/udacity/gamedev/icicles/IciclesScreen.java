package com.udacity.gamedev.icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class IciclesScreen implements Screen {

    public static final String TAG = IciclesScreen.class.getName();


    // ExtendViewport
    ExtendViewport iciclesViewport;

    // ShapeRenderer
    ShapeRenderer renderer;

    // TODO: Add ScreenViewport for HUD
    ScreenViewport HUD;

    // TODO: Add SpriteBatch
    SpriteBatch batch;

    // TODO: Add BitmapFont
    BitmapFont font;

    // Icicle
    Player player;
    Icicles icicles;

    // TODO: Add int to hold the top score
    int topScore;


    @Override
    public void show() {
        // Initialize the iciclesViewport using the world size constant
        iciclesViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        // Initialize the ShapeRenderer
        renderer = new ShapeRenderer();

        // Set autoShapeType(true) on the ShapeRenderer
        renderer.setAutoShapeType(true);

        // TODO: Initialize the HUD viewport
        HUD = new ScreenViewport();

        // TODO: Initialize the SpriteBatch
        batch = new SpriteBatch();

        // TODO: Initialize the BitmapFont
        font = new BitmapFont();

        // TODO: Give the font a linear TextureFilter
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        // Icicle in the middle of the world
        player = new Player(iciclesViewport);

        icicles = new Icicles(iciclesViewport);

        // TODO: Set top score to zero
        topScore = 0;
    }

    @Override
    public void resize(int width, int height) {
        // the iciclesViewport updates correctly
        iciclesViewport.update(width, height, true);

        // TODO: Update HUD viewport
        HUD.update(width, height, true);

        // TODO: Set font scale to min(width, height) / reference screen size
        font.getData().setScale(Math.min(width, height) / Constants.SCREEN_REFERENCE_SIZE);

        player.init();
        icicles.init();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        // TODO: Dispose of the SpriteBatch and font
        batch.dispose();
        font.dispose();
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

        // Draw the Icicles and player
        renderer.begin();
        //icicle.render(renderer);
        icicles.render(renderer);
        player.render(renderer);
        renderer.end();

        // TODO: Set the top score to max(topScore, iciclesDodges)
        topScore = Math.max(topScore, icicles.dodgedIcicles);

        // TODO: Apply the HUD viewport
        HUD.apply();

        // TODO: Set the SpriteBatch's projection matrix
        batch.setProjectionMatrix(HUD.getCamera().combined);

        // TODO: Begin the SpriteBatch
        batch.begin();

        // TODO: Draw the number of player deaths in the top left

        font.draw(batch, "Deaths: " + player.numberOfDeaths,
                Constants.HUD_MARGIN, HUD.getWorldHeight() - Constants.HUD_MARGIN);

        // TODO: Draw the score and top score in the top right
        font.draw(batch, "Score: " + icicles.dodgedIcicles + "\nTop Score: " + topScore,
                HUD.getWorldWidth() - Constants.HUD_MARGIN, HUD.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        // TODO: End the SpriteBatch
        batch.end();
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