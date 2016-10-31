package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class GameScreen extends InputAdapter implements Screen {

    public static final String TAG = GameScreen.class.getName();
    // prepare the game
    private SnakeGame mGame;

    private Viewport mSnakeViewport;
    private ShapeRenderer mRenderer;

    private ScreenViewport hudViewport;
    private BitmapFont mFont;
    private SpriteBatch mBatch;

    // prepare players

    private Snake mSnake;
    private Food mFood;

    private float mSecondsElapsed;

    private boolean mGameOver;

    private float speeder;

    // to save the last player score

    private Preferences preferences;

    private int savedScore;

    private int topScore;



    public GameScreen(SnakeGame game) {
        this.mGame = game;
    }

    @Override
    public void show() {



        speeder = 0.7f;

        mSnakeViewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        mRenderer = new ShapeRenderer();
        mRenderer.setAutoShapeType(true);

        hudViewport = new ScreenViewport();
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

        preferences = Gdx.app.getPreferences("my-preferences");

        topScore = preferences.getInteger("Top Score");

    }

    @Override
    public void resize(int width, int height) {
        mSnakeViewport.update(width, height, true);
        hudViewport.update(width, height, true);
        mFont.getData().setScale(Math.min(width, height) / 320.0f);

        mSnake.init();
        mFood.resetFoodPosition();
    }

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

        topScore = Math.max(topScore, mSnake.getScore());
        preferences.putInteger("Top Score", topScore);
        preferences.flush();


        if (!mGameOver) {

            drawBackground(mRenderer);

            mSnake.render(mRenderer);
            mFood.render(mRenderer);

            if (mSecondsElapsed > speeder) {

                mSnake.addBlock();
                if (mSnake.isDead()) {

                    mGameOver = true;
                }
                if (mSnake.hasEaten(mFood.getFoodPosition())) {
                    // check if the num of food item eaten are multiples of 10
                    // and encrease the speed
                    if (mSnake.getScore() % 10 == 0) {
                        speeder *= 0.9;
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
            hudViewport.apply();
            mBatch.setProjectionMatrix(hudViewport.getCamera().combined);
            mBatch.begin();
            mFont.setColor(1.0f, 0.8745f, 0.4901f, 1.0f);
            mFont.draw(mBatch, "Score: " + mSnake.getScore() + "\nTop Score: " + topScore,
                    hudViewport.getWorldWidth() - 30.0f, hudViewport.getWorldHeight() - 30.0f,
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

    @Override
    public void dispose() {
        mRenderer.dispose();
        mBatch.dispose();
        mFont.dispose();
    }

    private void drawGrid(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.set(ShapeType.Line);
        for (int i = 1; i < Constants.COLUMNS; i ++) {
            for (int j = 1; j < Constants.ROWS; j ++) {
                renderer.line(
                        i * Constants.BLOCK_SIZE, 0,
                        i * Constants.BLOCK_SIZE, Constants.WORLD_HEIGHT);
                renderer.line(
                        0, j * Constants.BLOCK_SIZE,
                        Constants.WORLD_WIDTH, j * Constants.BLOCK_SIZE
                );
            }
        }

    }

    private void drawBackground(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(Constants.BG_COLOR_1.r,
                Constants.BG_COLOR_1.g,
                Constants.BG_COLOR_1.b,
                Constants.BG_COLOR_1.a);


        renderer.rect(0, 0, mSnakeViewport.getWorldWidth(), mSnakeViewport.getWorldHeight());
    }

    private void drawBorder(ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.set(ShapeType.Line);
        renderer.line(0, 0, Constants.WORLD_WIDTH, 0);
        renderer.line(0, 0, 0, Constants.WORLD_HEIGHT);
        renderer.line(0, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        renderer.line(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, 0);
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

        Vector2 worldTouch = hudViewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.x > hudViewport.getWorldWidth() / 2.0f) {
            mSnake.turnClockWise();
        }

        if (worldTouch.x < hudViewport.getWorldWidth() / 2.0f) {
            mSnake.turnCounterClockWise();
        }

        if (mGameOver) {
            if (worldTouch.x > hudViewport.getWorldWidth() / 2 || worldTouch.x < hudViewport.getWorldWidth() / 2) {
                mSnake.update();
                mSnake.init();
                mGameOver = false;

            }
        }
        return true;
    }
}
