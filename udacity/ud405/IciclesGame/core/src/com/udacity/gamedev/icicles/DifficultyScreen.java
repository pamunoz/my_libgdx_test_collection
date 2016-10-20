package com.udacity.gamedev.icicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.udacity.gamedev.icicles.Constants.Difficulty;

// Set up DifficultyScreen

public class DifficultyScreen extends InputAdapter implements Screen {

    public static final String TAG = DifficultyScreen.class.getName();

    IciclesGame game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport difficultyViewport;

    BitmapFont font;

    public DifficultyScreen(IciclesGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        // TODO: Initialize a FitViewport with the difficulty world size constant
        difficultyViewport = new FitViewport(Constants.DIFFICULTY_WORLD_SIZE, Constants.DIFFICULTY_WORLD_SIZE);

        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();

        // TODO: Set the font scale using the constant we defined
        font.getData().setScale(Constants.DIFFICULTY_LABEL_SCALE);

        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

    }

    @Override
    public void render(float delta) {

        // TODO: Apply the viewport
        difficultyViewport.apply();

        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b, 1);

        // TODO: Set the ShapeRenderer's projection matrix
        renderer.setProjectionMatrix(difficultyViewport.getCamera().combined);

        // TODO: Use ShapeRenderer to draw the buttons
        renderer.begin(ShapeType.Filled);

        renderer.setColor(Constants.EASY_DIFFICULTY_COLOR);
        renderer.circle(Constants.EASY_CENTER.x, Constants.EASY_CENTER.y, Constants.DIFFICULTY_BUTTON_RADIUS);

        renderer.setColor(Constants.NORMAL_DIFFICULTY_COLOR);
        renderer.circle(Constants.NORMAL_CENTER.x, Constants.NORMAL_CENTER.y, Constants.DIFFICULTY_BUTTON_RADIUS);

        renderer.setColor(Constants.HARD_DIFFICULTY_COLOR);
        renderer.circle(Constants.HARD_CENTER.x, Constants.HARD_CENTER.y, Constants.DIFFICULTY_BUTTON_RADIUS);

        renderer.end();

        // TODO: Set the SpriteBatche's projection matrix
        batch.setProjectionMatrix(difficultyViewport.getCamera().combined);

        // TODO: Use SpriteBatch to draw the labels on the buttons
        // HINT: Use GlyphLayout to get vertical centering
        batch.begin();

        // Easy label
        GlyphLayout easyLayout = new GlyphLayout(font, Constants.EASY_LABEL);
        font.draw(batch, Constants.EASY_LABEL,
                Constants.EASY_CENTER.x,
                Constants.EASY_CENTER.y + easyLayout.height / 2, 0, Align.center, false);

        // Normal label
        GlyphLayout normalLayout = new GlyphLayout(font, Constants.NORMAL_LABEL);
        font.draw(batch, Constants.NORMAL_LABEL,
                Constants.NORMAL_CENTER.x,
                Constants.NORMAL_CENTER.y + normalLayout.height / 2, 0, Align.center, false);

        // Hard label
        GlyphLayout hardLayout = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.HARD_LABEL,
                Constants.HARD_CENTER.x,
                Constants.HARD_CENTER.y + hardLayout.height / 2, 0, Align.center, false );

        batch.end();



    }

    @Override
    public void resize(int width, int height) {
        difficultyViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }

    // Input

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // TODO: Unproject the touch from the screen to the world
        Vector2 worldTouch = difficultyViewport.unproject(new Vector2(screenX, screenY));

        // TODO: Check if the touch was inside a button, and launch the icicles screen with the appropriate difficulty

        if (worldTouch.dst(Constants.EASY_CENTER) < Constants.DIFFICULTY_BUTTON_RADIUS) {
            game.showIciclesScreen(Difficulty.EASY);
        }

        if (worldTouch.dst(Constants.NORMAL_CENTER) < Constants.DIFFICULTY_BUTTON_RADIUS) {
            game.showIciclesScreen(Difficulty.NORMAL);
        }

        if (worldTouch.dst(Constants.HARD_CENTER) < Constants.DIFFICULTY_BUTTON_RADIUS) {
            game.showIciclesScreen(Difficulty.HARD);
        }

        return true;
    }
}
