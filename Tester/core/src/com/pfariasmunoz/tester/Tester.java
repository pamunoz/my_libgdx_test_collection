package com.pfariasmunoz.tester;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Tester extends ApplicationAdapter{
	SpriteBatch batch;
    BitmapFont font;
	ShapeRenderer renderer;
	Vector2 position, velocity, foodPos;
    public static final float SCL = 60;
    float timeElapsed, speed, secondsPassed, lastTime;
    int seconds, xFood, yFood, columns, rows;
    Array<Vector2> tail;

    private static final Vector2 RIGHT = new Vector2(SCL, 0);
    private static final Vector2 LEFT = new Vector2(-SCL, 0);
    private static final Vector2 UP = new Vector2(0, SCL);
    private static final Vector2 DOWN = new Vector2(0, -SCL);

    public enum Direction {
        RIGHT, LEFT, UP, DOWN
    }
    private Direction direction;


	
	@Override
	public void create () {
        direction = Direction.RIGHT;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
        font.getData().setScale(2.0f);

        seconds = 0;
        columns = MathUtils.floor(Gdx.graphics.getWidth() / SCL);
        rows = MathUtils.floor(Gdx.graphics.getHeight() / SCL);
		renderer = new ShapeRenderer();
        position = new Vector2(0, 0);
        tail = new Array<Vector2>();
        tail.add(position);
        velocity = new Vector2(SCL, 0);

		batch = new SpriteBatch();
        timeElapsed = 0;
        speed = 1.0f;
        secondsPassed = 0.0f;
        lastTime = TimeUtils.nanoTime();

        resetFootLocation();

    }

	@Override
	public void render () {

		if (hasEaten()) {
			resetFootLocation();
		}

        myInput();
        // Update the difficulty
        secondsPassed += Gdx.graphics.getDeltaTime();
		if (secondsPassed > 2.0f) {
			speed *= 3.01f;
			secondsPassed = 0;
		}
        // update the position of the snake on the grid

        timeElapsed += Gdx.graphics.getDeltaTime();
		if (timeElapsed > (1.0f/speed)) {
			updateSnakePosition();
			timeElapsed = 0;
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.WHITE);
        // render snake
        for (Vector2 vector : tail) {
            renderer.rect(vector.x, vector.y, SCL, SCL);
        }
        // render food
		renderer.setColor(Color.GREEN);
		renderer.rect(foodPos.x, foodPos.y, SCL, SCL);
		renderer.end();

		renderer.begin(ShapeType.Line);
		drawGridLines();
		renderer.end();

        batch.begin();
        font.draw(batch, "Seconds: " + secondsPassed + "\nSpeed: " + speed,
                Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2, 0, Align.left, false);
        batch.end();


	}

	private void drawGridLines() {
		renderer.setColor(Color.BLUE);
		for (int i = 0; i < Gdx.graphics.getHeight(); i++) {
			renderer.line(0, i * SCL, Gdx.graphics.getWidth(), i * SCL);
		}
		for (int i = 0; i < Gdx.graphics.getWidth(); i++) {
			renderer.line(i * SCL, 0, i * SCL, Gdx.graphics.getHeight());
		}
	}

	private void updateSnakePosition() {
		position.x += velocity.x;
		position.y += velocity.y;
		ensureSnakeInBounds();
	}

	@Override
	public void dispose () {
        renderer.dispose();
		batch.dispose();
	}

	public void turn(Vector2 vector) {
		velocity.x = vector.x;
		velocity.y = vector.y;
	}

	private void myInput() {
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            direction = Direction.RIGHT;
			turn(RIGHT);
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            direction = Direction.LEFT;
			turn(LEFT);
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
            direction = Direction.UP;
			turn(UP);
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            direction = Direction.DOWN;
			turn(DOWN);
		}
	}

    public void resetFootLocation() {

        xFood = MathUtils.floor(MathUtils.random(columns));
        yFood = MathUtils.floor(MathUtils.random(rows));
        foodPos = new Vector2(xFood, yFood);
        foodPos.scl(SCL);

		ensureFoodInBounds();

    }

	private void ensureFoodInBounds() {
		if (foodPos.x > columns * SCL - SCL) {
            resetFootLocation();
        } else if (foodPos.x < 0) {
            resetFootLocation();
        }
		// limit vertical movement
		if (foodPos.y > rows * SCL - SCL) {
            resetFootLocation();
        } else if (foodPos.y < 0) {
            resetFootLocation();
        }
	}

	public boolean hasEaten() {
		if (position.dst(foodPos) < 1.0f) {
			return true;
		} else {
			return false;
		}
	}

    public void ensureSnakeInBounds() {
        // limit horizontal movement
        if (position.x > columns * SCL - SCL) {
            position.x = columns * SCL - SCL;
        } else if (position.x < 0) {
            position.x = 0;
        }
        // limit vertical movement
        if (position.y > rows * SCL - SCL) {
            position.y = rows * SCL - SCL;
        } else if (position.y < 0) {
            position.y = 0;
        }
    }



}
