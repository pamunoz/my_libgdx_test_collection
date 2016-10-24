package com.pfariasmunoz.tester;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Tester extends ApplicationAdapter{
	SpriteBatch batch;
	ShapeRenderer renderer;
	Vector2 position, velocity, foodPos;
    float timeElapsed, scl, speed;
    int seconds, xFood, yFood, columns, rows;
	
	@Override
	public void create () {

        seconds = 0;
        scl = 15;
        columns = MathUtils.floor(Gdx.graphics.getWidth() / scl);
        rows = MathUtils.floor(Gdx.graphics.getHeight() / scl);
		renderer = new ShapeRenderer();
        position = new Vector2(0, 0);
        velocity = new Vector2(scl, 0);

		batch = new SpriteBatch();
        timeElapsed = 0;
        speed = 1.0f;
        pickLocation();

    }

	@Override
	public void render () {

		if (hasEaten()) {
			pickLocation();
		}
		updateDifficulty();

        myInput();
        timeElapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // TODO: change this function beecause is updating not every second
        if (timeElapsed > (1.0f/speed)) {
            position.x += velocity.x;
            position.y += velocity.y;
            ensureInBounds();
            timeElapsed = 0;
        }
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.WHITE);
        // render snake
		renderer.rect(position.x, position.y, scl, scl);
        // render food
		renderer.setColor(Color.GREEN);
		renderer.rect(foodPos.x, foodPos.y, scl, scl);
		renderer.end();

		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLUE);
		for (int i = 0; i < Gdx.graphics.getHeight(); i++) {
			renderer.line(0, i * scl, Gdx.graphics.getWidth(), i * scl);
		}
		for (int i = 0; i < Gdx.graphics.getWidth(); i++) {
			renderer.line(i * scl, 0, i * scl, Gdx.graphics.getHeight());
		}
		renderer.end();
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
			turn(new Vector2(scl, 0));
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			turn(new Vector2(-scl, 0));
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
			turn(new Vector2(0, scl));
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			turn(new Vector2(0, -scl));
		}
	}

    public void pickLocation() {

        xFood = MathUtils.floor(MathUtils.random(columns));
        yFood = MathUtils.floor(MathUtils.random(rows));
        foodPos = new Vector2(xFood, yFood);
        foodPos.scl(scl);

        if (foodPos.x > columns * scl - scl) {
            pickLocation();
        } else if (foodPos.x < 0) {
            pickLocation();
        }
        // limit vertical movement
        if (foodPos.y > rows * scl - scl) {
            pickLocation();
        } else if (foodPos.y < 0) {
            pickLocation();
        }

    }

	public boolean hasEaten() {
		if (position.dst(foodPos) < 1.0f) {
			return true;
		} else {
			return false;
		}
	}

    public void ensureInBounds() {
        // limit horizontal movement
        if (position.x > columns * scl - scl) {
            position.x = columns * scl - scl;
        } else if (position.x < 0) {
            position.x = 0;
        }
        // limit vertical movement
        if (position.y > rows * scl - scl) {
            position.y = rows * scl - scl;
        } else if (position.y < 0) {
            position.y = 0;
        }
    }

	public void updateDifficulty() {
		if (timeElapsed > 1) {
			seconds++;
		}
		if (seconds > 10) {
			speed += 0.3;
			seconds = 0;
		}
	}

}
