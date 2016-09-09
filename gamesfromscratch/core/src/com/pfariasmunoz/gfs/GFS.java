package com.pfariasmunoz.gfs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GFS extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite sprite;
    BitmapFont font;
    GlyphLayout glyphLayout;
    String myText;
    String myText_2;
    float xPos;
    float yPos;


	
	@Override
	public void create () {

        xPos = Gdx.graphics.getWidth();
        yPos = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		img = new Texture("sprite-test.png");
		sprite = new Sprite(img);
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
						   Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
		sprite.setRotation(0f);
		sprite.setScale(10f, 6f);

        glyphLayout = new GlyphLayout();


        font = new BitmapFont(Gdx.files.internal("fonts/my_font.fnt"));
        myText = "Hola, como estan!";
        myText_2 = "Texto 2";
        glyphLayout.setText(font, myText_2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        // Draw the sprite
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth() / 2, sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
        // Draw my text
        font.draw(batch, myText, xPos / 2, yPos / 2);
        // Draw my text with the glyphLayout bounds
        font.draw(batch, glyphLayout, xPos / 2 - glyphLayout.width / 2 , yPos / 2 - glyphLayout.height / 2);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
