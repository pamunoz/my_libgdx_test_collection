package com.pfariasmunoz.tester;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Tester extends ApplicationAdapter{
	SpriteBatch batch;
	Texture img;
	ShapeRenderer renderer;
	Vector2 position, velocity;
    float timeElapsed, scl, speed;
    int seconds;
	
	@Override
	public void create () {
        seconds = 0;
        scl = 15;
		renderer = new ShapeRenderer();
        position = new Vector2(0, 0);
        velocity = new Vector2(scl, 0);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        timeElapsed = 0;
        speed = 1.0f;

	}

	@Override
	public void render () {
        myInput();
        if (seconds > 5) {
            speed += 0.1;
            seconds = 0;
        }
        timeElapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // TODO: change this function beecause is updating not every second
        if (timeElapsed > (1.0f)) {

            position.x += velocity.x;
            position.y += velocity.y;
            timeElapsed = 0;
            seconds++;
        }
		renderer.begin(ShapeType.Filled);
		renderer.rect(position.x, position.y, 15, 15);
		renderer.end();
	}
	
	@Override
	public void dispose () {
        renderer.dispose();
		batch.dispose();
		img.dispose();
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


}
