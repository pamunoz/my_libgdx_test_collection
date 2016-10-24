package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class GameScreen extends InputAdapter implements Screen {

    public static final String TAG = GameScreen.class.getName();

    private SnakeGame mGame;
    private ExtendViewport mSnakeViewport;
    private ShapeRenderer mRenderer;

    private Snake mSnake;
    private Food mFood;
    private Vector2 mPosition;

    private float mGridLength;

    private int mSeconds;

    public GameScreen(SnakeGame game) {
        this.mGame = game;
    }

    @Override
    public void show() {
        mGame = new SnakeGame();
        mSnakeViewport = new ExtendViewport(
                Constants.GAME_WORLD_WIDTH,
                Constants.GAME_WORLD_HEIGHT);
        mRenderer = new ShapeRenderer();
        mRenderer.setAutoShapeType(true);

        mGridLength = 15;
        mSnake = new Snake(mSnakeViewport, mGridLength);
        mFood = new Food(mSnakeViewport, mGridLength);


        Gdx.input.setInputProcessor(this);

        mSeconds = 0;
    }

    @Override
    public void resize(int width, int height) {
        mSnakeViewport.update(width, height, true);
        mSnake.init();
    }

    @Override
    public void render(float delta) {


        mSnakeViewport.apply(true);
        mSnake.update();
        mSnake.move();

        if (mSnake.hasEaten(mFood.getPosition())) {
            mFood.resetPosition();
        }
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mRenderer.setProjectionMatrix(mSnakeViewport.getCamera().combined);
        mRenderer.begin();
        mRenderer.setColor(Color.BLUE);
        mSnake.render(mRenderer);
        mRenderer.setColor(Color.WHITE);
        mFood.render(mRenderer);
        mRenderer.setColor(Color.CHARTREUSE);
        drawGridLines(mRenderer);
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
    }

    @Override
    public void dispose() {
        mRenderer.dispose();

    }

    private void drawGridLines(ShapeRenderer renderer) {
        renderer.set(ShapeType.Line);
        for (int i = 0; i < mSnakeViewport.getWorldHeight(); i++) {
            renderer.line(0, i * mGridLength, mSnakeViewport.getWorldWidth(), i * mGridLength);
        }
        for (int i = 0; i < mSnakeViewport.getWorldWidth(); i++) {
            renderer.line(i * mGridLength, 0, i * mGridLength, mSnakeViewport.getWorldHeight());
        }
    }

}
