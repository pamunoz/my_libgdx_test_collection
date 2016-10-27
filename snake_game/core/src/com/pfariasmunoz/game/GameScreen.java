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

    SnakeGame game;
    Direction direction;

    Viewport snakeViewport;
    ShapeRenderer renderer;

    ScreenViewport hudViewport;
    SpriteBatch batch;
    BitmapFont font;

    Snake snake;
    Food food;

    int topScore;
    int columns;
    int rows;

    public GameScreen(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        snakeViewport = new FitViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        hudViewport = new ScreenViewport();
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        snake = new Snake();
        food = new Food(snakeViewport);

        Gdx.input.setInputProcessor(this);

        topScore = 0;
        columns = MathUtils.floor(Constants.WORLD_SIZE / Constants.GRID_SIDE);
        rows = MathUtils.floor(Constants.WORLD_SIZE / Constants.GRID_SIDE);
        System.out.printf("Game width: " + snakeViewport.getWorldWidth());
        System.out.printf("Game height: " + snakeViewport.getWorldHeight());
        snakeViewport.getCamera().position.set(
                Constants.WORLD_SIZE / 2, Constants.WORLD_SIZE / 2, 0);
    }

    @Override
    public void resize(int width, int height) {
        snakeViewport.update(width, height, true);
        snakeViewport.getCamera().position.set(
                Constants.WORLD_SIZE / 2, Constants.WORLD_SIZE / 2, 0);
        hudViewport.update(width, height, true);
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }

    @Override
    public void render(float delta) {
        snake.move(delta);
        snake.update(delta);
        prepareScreen();
        renderer.setProjectionMatrix(snakeViewport.getCamera().combined);
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.RED);
//        renderer.rect(Constants.WORLD_SIZE - Constants.GRID_SIDE, 0,
//                Constants.GRID_SIDE, Constants.GRID_SIDE);

        snake.render(renderer);
        drawGrid(renderer);
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
        batch.dispose();

    }

    @Override
    public void dispose() {

    }

    private void drawGrid(ShapeRenderer renderer) {
        renderer.set(ShapeType.Line);
        renderer.setColor(Color.GRAY);
        for (int i = 0; i < columns + 1; i++) {

            for (int j = 0; j < rows + 1; j++) {
                renderer.line(
                        Constants.GRID_SIDE * i, 0,
                        Constants.GRID_SIDE * i, Constants.WORLD_SIZE);
                renderer.line(0, Constants.GRID_SIDE * j,
                        Constants.WORLD_SIZE, Constants.GRID_SIDE * j);
            }
        }
    }

    private void prepareScreen() {
        snakeViewport.apply(true);
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }
}
