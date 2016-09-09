package com.pfariasmunoz.gfs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GFS extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite sprite;
    BitmapFont font;
    String myText;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("sprite-test.png");
		sprite = new Sprite(img);
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
						   Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
		sprite.setRotation(0f);
		sprite.setScale(10f, 6f);

        font = new BitmapFont(Gdx.files.internal("fonts/my_font.fnt"));
        myText = "Hola, como estan!";
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth() / 2, sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
        font.draw(batch, myText, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
