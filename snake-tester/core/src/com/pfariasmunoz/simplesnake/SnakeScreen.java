package com.pfariasmunoz.simplesnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Pablo Farias on 27-10-16.
 */

public class SnakeScreen extends InputAdapter implements Screen {

    SpriteBatch batch;
    BitmapFont font;

    SimpleSnake mGame;

    Viewport mSnakeViewport;

    ShapeRenderer mRenderer;

    Array<Vector2> mTail;

    Vector2 mFoodPosition;

    float secondPassed;

    private boolean gameover;

    private Vector2 mPosition;
    // the index of the array of directions
    private int mDirection = 2;
    // TODO: change the array of directions to Array of Vector 2
    // x direction: down, up, right, left
    private float[] dx = {0, 0, Constants.BLOCK_SIZE, -Constants.BLOCK_SIZE};
    // y direction down, up, right, left
    private float[] dy = {-Constants.BLOCK_SIZE, Constants.BLOCK_SIZE, 0, 0};

    public SnakeScreen(SimpleSnake game) {
        this.mGame = game;
    }

    @Override
    public void show() {
        gameover = false;
        secondPassed = 0;
        mFoodPosition = new Vector2();
        resetFoodPosition();
        mSnakeViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        //mSnakeViewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        mRenderer = new ShapeRenderer();
        mRenderer.setAutoShapeType(true);
        mTail = new Array<Vector2>();

        Gdx.input.setInputProcessor(this);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(3.0f);
        init();
    }

    public void init() {
        mPosition = new Vector2();
        mTail.add(mPosition);
    }

    @Override
    public void render(float delta) {
        secondPassed += delta;

        mSnakeViewport.apply();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mRenderer.setProjectionMatrix(mSnakeViewport.getCamera().combined);
        mRenderer.begin();
        drawGrid(mRenderer);
        // draw the snake
        if (!gameover) {
            drawSnake(mRenderer);
            drawFood(mRenderer);


            // draw a block ahead removing the last one
            if (secondPassed > 0.5f) { // Gdx.graphics.getFramesPerSecond() % 5 == 0
                Vector2 newPosition = new Vector2(mTail.get(0).x + dx[mDirection], mTail.get(0).y + dy[mDirection]);
                //ensureSnakeInBounds(newPosition);
                mTail.insert(0, newPosition);
                // the conditions for dying
                if (mTail.get(0).x < 0 || mTail.get(0).y < 0 ||
                        mTail.get(0).x >= Constants.WORLD_WIDTH || mTail.get(0).y >= Constants.WORLD_HEIGHT) {
                    gameover = true;
                }
                // condition if the snake eats the food
                if (hasEaten(newPosition)) {
                    resetFoodPosition();
                } else {
                    // remove the last block
                    Vector2 someVector = mTail.pop();
                }
                secondPassed = 0;
            }

        } else {
            batch.setProjectionMatrix(mSnakeViewport.getCamera().combined);
            batch.begin();
            font.draw(batch, "GAME OVER", mSnakeViewport.getWorldWidth() / 4, mSnakeViewport.getWorldHeight() / 2);
            batch.end();
        }
        mRenderer.end();

    }



    @Override
    public void resize(int width, int height) {
        mSnakeViewport.update(width, height, true);

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
        batch.dispose();
    }

    @Override
    public void dispose() {
        mRenderer.dispose();
        batch.dispose();

    }
    // ============ Snake Methods =======================

    private boolean hasEaten(Vector2 position) {
        if (position.epsilonEquals(mFoodPosition, 0.2f)) {
            return true;
        } else {
            return false;
        }
    }

    private void drawSnake(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(0.8f, 0.8f, 1.0f, 1.0f);
        for (int i = 0; i < mTail.size; i++) {
            renderer.rect(mTail.get(i).x, mTail.get(i).y,
                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        }
    }

    private void ensureSnakeInBounds(Vector2 position) {
        // horizontal movement limitation
        if (position.x > Constants.BLOCK_SIZE * Constants.COLUMNS - Constants.BLOCK_SIZE) {
            position.x = Constants.BLOCK_SIZE * Constants.COLUMNS - Constants.BLOCK_SIZE;
        } else if (position.x < 0) {
            position.x = 0;
        }
        // vertical movement limietation
        if (position.y > Constants.BLOCK_SIZE * Constants.ROWS - Constants.BLOCK_SIZE) {
            position.y = Constants.BLOCK_SIZE * Constants.ROWS - Constants.BLOCK_SIZE;
        } else if (position.y < 0) {
            position.y = 0;
        }
    }

    // ========== Local Methods ===========


    /**
     * Draw a grid on the screen for debugging.
     * @param renderer The ShapeRenderer for drawing to the screen.
     */
    private void drawGrid(ShapeRenderer renderer) {
        //renderer.setColor(Color.WHITE);
        renderer.setColor(Color.RED);
        //renderer.setColor(0.5019607843f, 0.5019607843f, 0.5019607843f, 1.0f);
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

    // ============ Food Methods =======================

    private void drawFood(ShapeRenderer renderer) {
        renderer.set(ShapeType.Filled);
        renderer.setColor(Color.CHARTREUSE);
        renderer.rect(mFoodPosition.x, mFoodPosition.y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
    }

    private void resetFoodPosition() {
        int x = MathUtils.floor(MathUtils.random(Constants.COLUMNS));
        int y = MathUtils.floor(MathUtils.random(Constants.ROWS));
        mFoodPosition = new Vector2(x, y);
        mFoodPosition.scl(Constants.BLOCK_SIZE);
    }

    private void ensureFoodInBounds() {
        if (mFoodPosition.x > Constants.COLUMNS * Constants.BLOCK_SIZE - Constants.BLOCK_SIZE || mFoodPosition.x < 0) {
            resetFoodPosition();
        } else if (mFoodPosition.y > Constants.ROWS * Constants.BLOCK_SIZE - Constants.BLOCK_SIZE || mFoodPosition.y < 0) {
            resetFoodPosition();
        }
    }




    // ================== Input Handling ==============

    @Override
    public boolean keyDown(int keycode) {
        // change the direction (the index of the array of directions);
        // TODO: change the array of directions to Array of Vector 2
        // TODO: test the rotate90 of Vector2 to rotate
        int newDirection = keycode == Keys.S ? 0 : (keycode == Keys.W ? 1 : (keycode == Keys.D) ? 2 : (keycode == Keys.A ? 3 : -1));
        if (newDirection != -1) mDirection = newDirection;
        // if it is gameover and is press any key
        // TODO: change the init method, is not reseting the game
        // https://www.youtube.com/watch?v=JGW5ecDOjjk the video of the tutorial 8:42 min
        if (gameover = true && keycode == Keys.ANY_KEY) {
            gameover = false;
            init();
        }
        return super.keyDown(keycode);

    }
}