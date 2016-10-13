package com.pfariasmunoz.snaky;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SnakeGame extends ApplicationAdapter implements InputProcessor {

	Vector2 position;
	Vector2 speed;
	ShapeRenderer renderer;
	float headSize;
	float screenWith;
	float screenHeight;

	
	@Override
	public void create () {
		position = new Vector2();
		speed = new Vector2();
		renderer = new ShapeRenderer();
		headSize = Gdx.graphics.getHeight() / 48f;
		screenWith = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
		snake();

	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		showSnakeHead();
		updateSnake();

	}


	
	@Override
	public void dispose () {
		renderer.dispose();
	}

	private void snake() {
		position.x = 0;
		position.y = 0;
		speed.x = 1;
		speed.y = 0;
	}

	private void updateSnake() {
		position.x += speed.x * headSize;
		position.y += speed.y * headSize;
	}

	private void showSnakeHead() {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(1f, 1f, 1f, 1f);
		renderer.rect(position.x, position.y, headSize, headSize);
		renderer.end();
	}

	// ============= Input Handling ===============

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.UP) {
			speed.x = 0;
			speed.y = 1;
			if (position.y > screenHeight) {
				position.y = screenHeight - headSize;
			}
		}
		if (keycode == Keys.DOWN) {
			speed.x = 0;
			speed.y = -1;
		}
		if (keycode == Keys.LEFT) {
			speed.x = -1;
			speed.y = 0;
		}
		if (keycode == Keys.RIGHT) {
			speed.x = 1;
			speed.y = 0;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private void stopSnake() {
		speed.x = 0;
		speed.y = 0;
	}
}
