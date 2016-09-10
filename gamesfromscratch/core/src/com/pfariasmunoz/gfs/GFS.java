package com.pfariasmunoz.gfs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class GFS extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    Texture img2;
	Sprite sprite;
    BitmapFont font;
    GlyphLayout glyphLayout;
    String myText;
    String myText_2;
    String myText_3;
    float xPos;
    float yPos;

	@Override
	public void create () {

        xPos = Gdx.graphics.getWidth();
        yPos = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		img = new Texture("sprite-test.png");
        img2 = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
						   Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
		sprite.setRotation(0f);
		sprite.setScale(10f, 6f);


        glyphLayout = new GlyphLayout();


        font = new BitmapFont(Gdx.files.internal("fonts/my_font.fnt"));
        myText = "Hola, como estan!";
        myText_2 = "Texto 2";
        myText_3 = "I took one, one couse you left me\n"
                + "Two, two for my family\n"
                + "Three, three for my heartache";
        glyphLayout.setText(font, myText_2);
	}

	@Override
	public void render () {
        // testing polling for the keyboard
        if (Gdx.input.isKeyPressed(Keys.LEFT)) sprite.translateX(-10f);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) sprite.translateX(2f);
        if (Gdx.input.isKeyPressed(Keys.UP)) sprite.translateY(1f);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) sprite.translateY(-1f);

        // testing polling for the mouse or touch
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            sprite.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        // Draw the sprite
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth() / 2, sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());

        // Draw my text
        font.draw(batch, myText, xPos / 2, yPos - (yPos * 0.1f));
        // Draw my text with the glyphLayout bounds
        font.draw(batch, glyphLayout, xPos / 2 - glyphLayout.width / 2 , yPos / 2 - glyphLayout.height / 2);
        // Draw a multi line
        // and change color and aligment
        font.setColor(Color.RED);
        font.draw(batch, myText_3, xPos / 2 - 16, yPos / 2, xPos / 2, Align.right, true);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
