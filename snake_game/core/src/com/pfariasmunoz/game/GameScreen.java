package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pfariasmunoz.game.Constants.Direction;



public class GameScreen extends InputAdapter implements Screen {

    public static final String TAG = GameScreen.class.getName();
    // prepare the game
    private SnakeGame mGame;
    private SpriteBatch mBatch;
    private BitmapFont mFont;
    private Viewport mSnakeViewport;
    private ShapeRenderer mRenderer;

    // prepare players

    private Snake mSnake;
    private Food mFood;

    private float mSecondsElapsed;

    private boolean mGameover;

    private static final Vector2[] directions = {
            Direction.DOWN.getDir(),
            Direction.UP.getDir(),
            Direction.RIGHT.getDir(),
            Direction.LEFT.getDir()};
    private int dir = 2;


    public GameScreen(SnakeGame game) {
        this.mGame = game;
    }

    @Override
    public void show() {
        mBatch = new SpriteBatch();

        mFont = new BitmapFont();
        mFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mFont.getData().setScale(3.0f);

        mSnakeViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        mRenderer = new ShapeRenderer();
        mRenderer.setAutoShapeType(true);

        mSnake = new Snake(mSnakeViewport);
        mFood = new Food(mSnakeViewport);
        mFood.resetFoodPosition();

        mSecondsElapsed = 0;
        mGameover = false;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        mSnakeViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        mSecondsElapsed += delta;

        mSnakeViewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mRenderer.setProjectionMatrix(mSnakeViewport.getCamera().combined);
        mRenderer.begin();
        //drawGrid(mRenderer);

        if (!mGameover) {
            mSnake.render(mRenderer);
            mFood.render(mRenderer);

            if (mSecondsElapsed > 0.2f) {
                mSnake.addBlock();
//                if (mSnake.isDead()) {
//                    mGameover = true;
//                }

                if (mSnake.getTail().get(0).epsilonEquals(mFood.getFoodPosition(), 0.02f)) {
                    mFood.resetFoodPosition();
                } else {
                    mSnake.removeLastElement();
                }

                mSecondsElapsed = 0;
            }

        } else {
            mBatch.setProjectionMatrix(mSnakeViewport.getCamera().combined);
            mBatch.begin();
            mFont.draw(mBatch, "GAME OVER", mSnakeViewport.getWorldWidth() / 4, mSnakeViewport.getWorldHeight() / 2);
            mBatch.end();
        }

        mRenderer.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
        mRenderer.setColor(Color.RED);
        mRenderer.set(ShapeType.Line);
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

}
