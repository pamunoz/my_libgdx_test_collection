package com.pfariasmunoz.camerademo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CameraDemo extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Sprite sprite;
	OrthographicCamera camera;
	final float GAME_WORLD_WIDTH = 100;
	final float GAME_WORLD_HEIGHT = 50;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal("sample.png")));
		sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		// Aspect ration
		float aspectRatio = (float)Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();

		camera = new OrthographicCamera(GAME_WORLD_HEIGHT * aspectRatio, GAME_WORLD_HEIGHT);
		camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
		camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		sprite.getTexture().dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) camera.translate(-1f, 0f);
		if (keycode == Keys.RIGHT) camera.translate(1f, 0f);
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
}
