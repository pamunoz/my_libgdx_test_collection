package com.pfariasmunoz.gfs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class GFS extends ApplicationAdapter implements InputProcessor {
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
    float scale = 1f;
    Sound fartSound;

    Music song1;
    Music song2;

	@Override
	public void create () {
        // Add music
        song1 = Gdx.audio.newMusic(Gdx.files.internal("some_music.mp3"));
        song2 = Gdx.audio.newMusic(Gdx.files.internal("clutch.mp3"));

        // playing the songs
        // there is no instances
        // no multiple intances of music
        song1.play();
        // when song1 finish we start song2
        song1.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                song2.play();
            }
        });

        // testing sound
        fartSound = Gdx.audio.newSound(Gdx.files.internal("fart.ogg"));
        final long ourSoundId = fartSound.loop(1.0f, 1.0f, 0.0f);

        // second instance of the same sound
        fartSound.loop(1.0f, 2.0f, 0.0f);

        // start our sound in a loop, and after 10 seconds it is going to stop
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                fartSound.pause(ourSoundId); // pause one instance
                fartSound.pause(); // pause all instances of pause
            }
        }, 2);


        xPos = Gdx.graphics.getWidth();
        yPos = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		img = new Texture("sprite-test.png");
        img2 = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
						   Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
		sprite.setRotation(0f);
		sprite.setScale(scale);


        glyphLayout = new GlyphLayout();


        font = new BitmapFont(Gdx.files.internal("fonts/my_font.fnt"));
        myText = "Hola, como estan!";
        myText_2 = "Texto 2";
        myText_3 = "I took one, one couse you left me\n"
                + "Two, two for my family\n"
                + "Three, three for my heartache";
        glyphLayout.setText(font, myText_2);

        // When we implement the InputProcessor we have to register it
        Gdx.input.setInputProcessor(this);
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
        song1.dispose();
        song2.dispose();
        fartSound.dispose();
	}

    //=====Add Event driven inputs ===


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) sprite.translateX(-1f);
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
        sprite.setPosition(screenX, Gdx.graphics.getHeight() - screenY );
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        if (amount > 0) sprite.setScale(scale++);
        if (amount < 0) sprite.setScale(scale--);
        return false;
    }
}
