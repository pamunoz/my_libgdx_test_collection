package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * GameScreen.java
 * Purpose: This is the Screen of the game.
 *
 * @author Pablo Muñoz.
 * @version 1.0 10/07/2016
 */

public class GameScreen extends InputAdapter implements Screen {

    public static final String TAG = GameScreen.class.getName();

    private Viewport mSnakeViewport;
    private ShapeRenderer mRenderer;

    private ScreenViewport mHudViewport;
    private BitmapFont mFont;
    private SpriteBatch mBatch;

    // prepare players

    private Snake mSnake;
    private Food mFood;

    private float mSecondsElapsed;

    private boolean mGameOver;

    private float mSpeeder;

    // to save the last player score

    private Preferences mPreferences;

    private int mTopScore;

    /**
     * Construct a screen for the snake game.
     *
     * @param game that uses this screen.
     */
    public GameScreen(SnakeGame game) {
        SnakeGame mGame = game;
    }

    /**
     * Initialize the member variables of this screen.
     */
    @Override
    public void show() {

        mSpeeder = 0.7f;

        mSnakeViewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        mRenderer = new ShapeRenderer();
        mRenderer.setAutoShapeType(true);

        mHudViewport = new ScreenViewport();
        mBatch = new SpriteBatch();

        mFont = new BitmapFont();
        mFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        //mFont.getData().setScale(3.0f);

        mSnake = new Snake(mSnakeViewport);
        mFood = new Food(mSnakeViewport);
        mFood.resetFoodPosition();

        Gdx.input.setInputProcessor(this);

        mGameOver = false;

        mSecondsElapsed = 0;

        mPreferences = Gdx.app.getPreferences("my-mPreferences");

        mTopScore = mPreferences.getInteger("Top Score");

    }

    /**
     * Update the screen when the screen resize.
     *
     * @param width the width of the screen.
     * @param height the height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        mSnakeViewport.update(width, height, true);
        mHudViewport.update(width, height, true);
        mFont.getData().setScale(Math.min(width, height) / 320.0f);

        mSnake.init();
        mFood.resetFoodPosition();
    }

    /**
     * Render the game elements to the screen.
     *
     * @param delta the time that has passed since the last frame.
     */
    @Override
    public void render(float delta) {

        if (mGameOver) {
            mFont.getData().setScale(3.0f);
        } else {
            mFont.getData().setScale(1.5f);
        }
        mSnake.update();

        mSecondsElapsed += delta;
        mGameOver = mSnake.isDead();
        mSnakeViewport.setScreenPosition(Gdx.graphics.getWidth() / 5, 0);

        mSnakeViewport.apply();
        Gdx.gl.glClearColor(
                Constants.BG_COLOR_2.r, Constants.BG_COLOR_2.g, Constants.BG_COLOR_2.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the snake
        mRenderer.setProjectionMatrix(mSnakeViewport.getCamera().combined);
        mRenderer.begin();



        //drawBorder(mRenderer);
        // drawGrid(mRenderer);

        mTopScore = Math.max(mTopScore, mSnake.getScore());
        mPreferences.putInteger("Top Score", mTopScore);
        mPreferences.flush();


        if (!mGameOver) {
            drawBackground(mRenderer);

            mSnake.render(mRenderer);
            mFood.render(mRenderer);

            if (mSecondsElapsed > mSpeeder) {

                mSnake.addBlock();
                if (mSnake.isDead()) {
                    mSpeeder = 0.7f;
                    mGameOver = true;
                }
                if (mSnake.hasEaten(mFood.getFoodPosition())) {
                    // check if the num of food item eaten are multiples of 10
                    // and encrease the speed
                    if (mSnake.getScore() % 10 == 0) {
                        mSpeeder *= 0.9;
                    }
                    mFood.resetFoodPosition();
                    while (mSnake.isColliding(mFood.getFoodPosition())) {
                        mFood.resetFoodPosition();
                    }
                } else {
                    mSnake.removeLastElement();
                }
                mSecondsElapsed = 0;
            }
        } else {
            mBatch.setProjectionMatrix(mSnakeViewport.getCamera().combined);
            mBatch.begin();
            mFont.setColor(Constants.APPLE_COLOR_3.r, Constants.APPLE_COLOR_3.g, Constants.APPLE_COLOR_3.b, 1.0f);
            mFont.draw(mBatch, "GAME OVER" + "\nScore: " + mSnake.getScore(), mSnakeViewport.getWorldWidth() /3, mSnakeViewport.getWorldHeight() / 2, 0, Align.center, true);
            mBatch.end();
        }
        mRenderer.end();
        if (!mGameOver) {
            mHudViewport.apply();
            mBatch.setProjectionMatrix(mHudViewport.getCamera().combined);
            mBatch.begin();
            mFont.setColor(1.0f, 0.8745f, 0.4901f, 1.0f);
            mFont.draw(mBatch, "Score: " + mSnake.getScore() + "\nTop Score: " + mTopScore,
                    mHudViewport.getWorldWidth() - 30.0f, mHudViewport.getWorldHeight() - 30.0f,
                    0, Align.right, false);
            mBatch.end();

        }
    }

    @Override
    public void hide() {
        mRenderer.dispose();
        mBatch.dispose();
        mFont.dispose();
    }

    /**
     * disposes of the game resources used.
     */
    @Override
    public void dispose() {
        mRenderer.dispose();
        mBatch.dispose();
        mFont.dispose();
    }

    /**
     *
     * @param renderer
     */
    private void drawBackground(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(Constants.BG_COLOR_1.r,
                Constants.BG_COLOR_1.g,
                Constants.BG_COLOR_1.b,
                Constants.BG_COLOR_1.a);
        renderer.rect(0, 0, mSnakeViewport.getWorldWidth(), mSnakeViewport.getWorldHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ANY_KEY && mGameOver) {
            System.out.println("Game over: ");
            mGameOver = false;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = mHudViewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.x > mHudViewport.getWorldWidth() / 2.0f) {
            mSnake.turnClockWise();
        }

        if (worldTouch.x < mHudViewport.getWorldWidth() / 2.0f) {
            mSnake.turnCounterClockWise();
        }

        if (mGameOver) {
            if (
                    worldTouch.x > mHudViewport.getWorldWidth() / 2 ||
                    worldTouch.x < mHudViewport.getWorldWidth() / 2) {
                mSnake.update();
                mSnake.init();
                mGameOver = false;

            }
        }
        return true;
    }
}
