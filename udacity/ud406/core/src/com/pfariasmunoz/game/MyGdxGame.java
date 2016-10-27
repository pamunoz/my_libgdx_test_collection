package com.pfariasmunoz.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	public final static String TAG = "MyClass";
	SpriteBatch batch;
	Texture img;
	Vector2 mPosition;
	Vector2 mDirection;
	
	@Override
	public void create () {
//		Gdx.app.setLogLevel(Application.LOG_NONE); // Don't log anything
//		Gdx.app.setLogLevel(Application.LOG_ERROR); // Never mind. Only log error messages.
//		Gdx.app.error(TAG, "Oh nooooo! We're about to crash or display an error message to the user");
//		Gdx.app.setLogLevel(Application.LOG_INFO); // Log informational messages as well as error messages
		Gdx.app.log(TAG, "The aplication type is: " + Gdx.app.getType());
//		Gdx.app.setLogLevel(Application.LOG_DEBUG); // Actually, you know what, log everything
//		Gdx.app.debug(TAG, "Experimental feature worked!");
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Gdx.input.setInputProcessor(this);
		mPosition = new Vector2();
		mDirection = new Vector2(10, 0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, mPosition.x, mPosition.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
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
