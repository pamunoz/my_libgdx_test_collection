package com.pfariasmunoz.camerademo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CameraDemo extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite sprite;
	OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal("sample.png")));
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
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
}
