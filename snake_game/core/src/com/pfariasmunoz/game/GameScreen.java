package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

    private int topScore;

    public GameScreen(SnakeGame game) {
        this.mGame = game;
    }

    @Override
    public void show() {

        mSnakeViewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        mRenderer = new ShapeRenderer();
        mRenderer.setAutoShapeType(true);

        hudViewport = new ScreenViewport();
        mBatch = new SpriteBatch();

        mFont = new BitmapFont();
        mFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mFont.getData().setScale(3.0f);

        mSnake = new Snake(mSnakeViewport);
        mFood = new Food(mSnakeViewport);
        mFood.resetFoodPosition();

        Gdx.input.setInputProcessor(this);

        topScore = 0;


        mGameOver = false;

        mSecondsElapsed = 0;
    }

    @Override
    public void resize(int width, int height) {
        mSnakeViewport.update(width, height, true);
        hudViewport.update(width, height, true);
        mFont.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);

        mSnake.init();
        mFood.resetFoodPosition();
    }

    @Override
    public void render(float delta) {
        mSnake.update();

        mSecondsElapsed += delta;
        mGameOver = mSnake.isDead();

        mSnakeViewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Render the snake
        mRenderer.setProjectionMatrix(mSnakeViewport.getCamera().combined);
        mRenderer.begin();
        drawBorder(mRenderer);
        // drawGrid(mRenderer);

        if (!mGameOver) {

            mSnake.render(mRenderer);
            mFood.render(mRenderer);

            if (mSecondsElapsed > 0.2f) {
                mSnake.addBlock();
//                if (mSnake.isDead()) {
//                   mGameOver = true;
//                }
                if (mSnake.hasEaten(mFood.getFoodPosition())) {
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
            mFont.draw(mBatch, "GAME OVER", mSnakeViewport.getWorldWidth() / 6, mSnakeViewport.getWorldHeight() / 2);
            mBatch.end();
        }

        mRenderer.end();

        hudViewport.apply();
        mBatch.setProjectionMatrix(hudViewport.getCamera().combined);
        mBatch.begin();

        topScore = Math.max(topScore, mSnake.getScore() - 1);

        mFont.draw(mBatch, "Score: " + mSnake.getScore(),
                hudViewport.getWorldWidth() - 30.0f, hudViewport.getWorldHeight() - 30.0f,
                0, Align.right, false);
        mBatch.end();



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
}
