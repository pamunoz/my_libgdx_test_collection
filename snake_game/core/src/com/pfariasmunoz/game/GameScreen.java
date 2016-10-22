package com.pfariasmunoz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class GameScreen extends InputAdapter implements Screen {

    public static final String TAG = GameScreen.class.getName();

    SnakeGame game;
    ExtendViewport snakeViewport;
    ShapeRenderer renderer;

    Snake snake;
    Vector2 pos;

    int topScore;

    public GameScreen(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        snakeViewport = new ExtendViewport(
                Constants.GAME_WORLD_WIDTH,
                Constants.GAME_WORLD_HEIGHT);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        pos = new Vector2(0, 0);

        snake = new Snake(snakeViewport);

        Gdx.input.setInputProcessor(this);

        topScore = 0;
    }

    @Override
    public void resize(int width, int height) {
        snakeViewport.update(width, height, true);
        snake.init();
    }

    @Override
    public void render(float delta) {
        snake.update(delta);

        snakeViewport.apply(true);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(snakeViewport.getCamera().combined);
        renderer.begin(ShapeType.Filled);
        snake.render(renderer);
        renderer.end();

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }

}
